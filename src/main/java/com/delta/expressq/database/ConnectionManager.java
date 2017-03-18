package com.delta.expressq.database;

import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.sql.Time;
import java.util.Map;

import com.delta.expressq.util.*;

public class ConnectionManager {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/heroku_ce661b81b9c9192";
    // Database credentials
    private static final String DB_USER = "b02576368bd1b5";
    private static final String DB_PASS = "6d1d4ae1";

    // Method controlling connections to the database
    private static Connection getConnection() throws ConnectionManagerException {
        try {
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			conn.setAutoCommit(true);
            return conn;
        } catch (SQLException sqle) {
            System.out.println("Couldn't connect to database.");
            throw new ConnectionManagerException("Failed to connext to database.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
            throw new ConnectionManagerException("Failed to connext to database.");
        }
    }

    private static void cleanup(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }

            if (conn != null) {
                conn.close();
                conn = null;
            }

            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (Exception e) {
            System.out.println("[NOT CRITICAL] Clean-up exception: " + e.getMessage());
        }
    }


    public static String getUsername(int userID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Username FROM User WHERE UserID = ?");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            String username = "";

            if (rs.next())
                username = rs.getString("Username");

            cleanup(conn, pstmt, rs);
            return username;

        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

   /**
	*	Method gets transaction object from database.
	*	@param 		transactionID 	id of needed transaction
	*	@param		name 			venue name
	*	@param		APIpass 		venue password for mobile application
	*	@returns 	details of needed transaction as transaction object
	*/
    public static Transaction getTransaction(String name, String APIpass, int transactionID) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT User.Username, User.Fname, User.Lname, TransactionID, "
					+ "User.UserID, Keywords, Transaction.VenueID, TotalPrice, Time, CollectionTime, Status "
                    + "FROM Transaction, Venue, User "
                    + "WHERE TransactionId = ? "
                    + "AND Venue.VenueID = Transaction.VenueID "
                    + "AND Venue.Name =  ? "
                    + "AND Venue.APIpass =  ? "
					+ "AND Transaction.UserID = User.UserID ");
            pstmt.setInt(1, transactionID);
            pstmt.setString(2, name);
            pstmt.setString(3, APIpass);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a transaction was returned.
            Transaction transaction = null;
            if (rs.next()) {
                int userID = rs.getInt("UserID");
                int venueID = rs.getInt("VenueID");
                float total = rs.getFloat("TotalPrice");
                String date = rs.getTimestamp("Time").toString();
                int status = rs.getInt("Status");
				String fullname = rs.getString("Fname") + " " + rs.getString("Lname");
				Time collection = rs.getTime("CollectionTime");
				String keywords = rs.getString("Keywords");
				String username = rs.getString("Username");
                transaction = new Transaction(transactionID, userID, venueID, total, date, status, keywords, collection, username, fullname);
            }
            cleanup(conn, pstmt, rs);
            return transaction;

        } catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
            throw new ConnectionManagerException(sqle);
        }
    }

   /**
	*	Method gets ids of upcoming transactions - paid goods, which will be collected within x minutes.
	*	@param 		minutes 		only show transactions planned within this time from now
	*	@param		name 			venue name
	*	@param		APIpass 		venue password for mobile application
	*	@returns 	list of ids (integers)
	*/
	public static List<Integer> getIDsOfUpcomingTransactions(String name, String APIpass, int minutes) throws ConnectionManagerException {
		PreparedStatement pstmt;
		List<Integer> ids = new ArrayList<Integer>();
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT TransactionID "
                    + " FROM Transaction, Venue"
                    + " WHERE Venue.VenueID = Transaction.VenueID "
					+ " AND CAST(Transaction.Time AS DATE) = CAST(CURRENT_TIMESTAMP AS DATE) "
					+ " AND CollectionTime < CAST((DATE_ADD(CURRENT_TIMESTAMP, INTERVAL ? minute)) AS TIME) "
					+ " AND Status = 0 "
                    + " AND Venue.Name =  ? "
                    + " AND Venue.APIpass =  ?"
					+ " AND CollectionTime <> CAST('00:00:00' AS TIME)"); //exclude ones with unset time
            pstmt.setInt(1, minutes);
            pstmt.setString(2, name);
            pstmt.setString(3, APIpass);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("TransactionID"));
            }
            cleanup(conn, pstmt, rs);
            return ids;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
	}

	/**
	*	Method gets items and their quantities for given transaction.
	*	@param 		transactionID 	id of transaction
	*	@returns 	hashmap of item and quantity
	*/
    public static HashMap getItemsInTransaction(int transactionID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Name, Quantity "
                    + "FROM ItemQuantity, Item "
                    + "WHERE ItemQuantity.TransactionId = ? "
                    + "AND ItemQuantity.ItemID = Item.ItemID ");
            pstmt.setInt(1, transactionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                hmap.put(rs.getString("Name"), rs.getInt("Quantity"));
            }
            cleanup(conn, pstmt, rs);
            return hmap;

        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    public static boolean checkUserName(String username) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            // Query returning a user with matching username
            pstmt = conn.prepareStatement("SELECT * FROM User WHERE Username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            boolean exists = rs.next();
            cleanup(conn, pstmt, rs);
            return exists;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    /**
     * Adds a new user to the database
     *
     * @param username	username value to be written to database
     * @param password	hashed password value to be wrriten to database
     * @param email		email value to be written to database
     * @param fname		first name value to be written to database
     * @param lname		last name value to br written to database
     * @return			if sql insert cannot be run return false
     * @throws ConnectionManagerException
     */
    public static boolean writeUser(String username, String password, String email, String fname, String lname) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("INSERT into User (Fname, Lname, email, Username, Password) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.executeUpdate();
            ResultSet tableKeys = pstmt.getGeneratedKeys();
            tableKeys.next();
            int autoGeneratedID = tableKeys.getInt(1);
            cleanup(conn, pstmt, tableKeys);
            return false;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    /**
     * @deprecated
     * @param name
     * @return
     * @throws ConnectionManagerException
     */
    public static int getVenueID(String name) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM Venue WHERE Name = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            int venueID = -1;
            if (rs.next())
                venueID = rs.getInt("VenueID");

            cleanup(conn, pstmt, rs);
            return venueID;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

   /**
	*	Method fills provided data structure with items available at given venue.
	*	@param 		items			datastructure for items
	*	@param		venueID 		id of venue
	*/
    public static void setItems(Map<String, Map<String, ArrayList<Item>>> items, String venueID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Item.ItemID AS ItemID, Item.Name AS ItemName, Item.Price as ItemPrice, Section.SectionID, "
                    + " Section.Description AS SectionName, "
                    + " Menu.MenuID, Menu.VenueID, Menu.Description AS MenuName "
                    + "FROM Menu, Section, Item "
                    + "WHERE Menu.VenueID = ?"
                    + "AND Menu.MenuID = Section.MenuID "
                    + "AND Section.SectionID = Item.SectionID");
            pstmt.setString(1, venueID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String menuName = rs.getString("MenuName");
                if (!items.containsKey(menuName))
                    items.put(menuName, new HashMap<String, ArrayList<Item>>());

                String sectionName = rs.getString("SectionName");
                if (!items.get(menuName).containsKey(sectionName))
                    items.get(menuName).put(sectionName, new ArrayList<Item>());

                items.get(menuName).get(sectionName).add(
                        new Item(
                                rs.getDouble("ItemPrice"),
                                rs.getString("ItemName"),
                                rs.getInt("ItemID")
                        )
                );

            }
            cleanup(conn, pstmt, rs);

        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

   /**
	*	Method fills provided data structure with pairs of venue name and its id.
	*	@param 		venues 			map of String and Integer to be filled with names and ids
	*/
    public static void setVenues(Map<String, Integer> venues) throws ConnectionManagerException {
        Statement stmt;
        try {
			Connection conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name, VenueID FROM Venue");
            while (rs.next()) {
                venues.put(rs.getString("Name"), rs.getInt("VenueID"));
            }
            cleanup(conn, null, rs);
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

   /**
	*	Method gets details of requested items.
	*	@param 		ids				list of items' ids
	*	@returns 	list of Item objects
	*/
    public static ArrayList<Item> getItemsByIDs(List<Integer> ids) throws ConnectionManagerException {
        //to avoid sending unprepared statements (query had one hardcoded Item)
        ArrayList<Item> items = new ArrayList<Item>();
        if (ids.size() == 0)
            return items;

        //System.out.println(ids.toString());

        StringBuilder query = new StringBuilder("SELECT Item.ItemID as ID, Item.Name AS Name, Item.Price as Price FROM Item WHERE Item.ItemID = ? ");

        for (int i = 1; i < ids.size(); i++) {
            query.append("OR Item.ItemID = ? ");
        }

        try {
			Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query.toString());
            for (int i = 0; i < ids.size(); i++) {
                pstmt.setInt(i + 1, ids.get(i));
            }
            //System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
                items.add(new Item(rs.getDouble("Price"), rs.getString("Name"), rs.getInt("ID")));

            cleanup(conn, pstmt, rs);
            return items;
        } catch (SQLException sqle) {
            System.out.println("SQL query failed: " + sqle.getMessage());
            throw new ConnectionManagerException(sqle);
        }
    }


   /**
	*	Method puts new order into database.
	*	@param 		user			username of customer
	*	@param		order			Order object containing order details
	*	@returns 	id of just added transaction
	*/
    public static int newOrder(String user, Order order) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            conn.setAutoCommit(false);

            //add transaction
            PreparedStatement pstmt = conn.prepareStatement("INSERT into transaction (UserID, VenueID, TotalPrice, Status, CollectionTime, Keywords) "
                            + " VALUES ((SELECT UserID FROM user WHERE Username = ?), ?, ?, 0, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user);
            pstmt.setInt(2, order.getVenue());
            pstmt.setDouble(3, order.getTotal());
            pstmt.setString(4, order.getCollectionTime());
            pstmt.setString(5, order.getKeywords());
            pstmt.executeUpdate();

            //Obtain transaction id
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int transactionID = rs.getInt(1);

            //add every item from order
            for (Map.Entry<Item, Integer> entry : order.getItemsAndQuantities().entrySet()) {
                pstmt = conn.prepareStatement("INSERT INTO `heroku_ce661b81b9c9192`.`itemquantity` (`ListID`, `ItemID`, `Quantity`, `TransactionID`) "
                        + "VALUES (NULL, ?, ?, ?)");
                pstmt.setInt(1, entry.getKey().getID());
                pstmt.setInt(2, entry.getValue());
                pstmt.setInt(3, transactionID);
                pstmt.executeUpdate();
            }

            //commit transaction (database one, not financial)
            conn.commit();
            cleanup(conn, pstmt, rs);
            return transactionID;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }

    }

    /**
     * This retrieves the data from the user table and passes it back into AdminAction so all the users' details can be displayed on screen
     *
     * @param users List that holds the values of each record in the user table.
     * @return users is returned if the query is successful, otherwise it returns null.
     */
    public static List DisplayUsers(List users) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            User user;
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setFname(rs.getString("Fname"));
                user.setLname(rs.getString("Lname"));
                user.setemail(rs.getString("email"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setAdmin(rs.getInt("Type"));
                users.add(user);
            }
            cleanup(conn, null, rs);
            return users;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    /**
     * This method receives an array of UserIDs that have been chosen to be deleted from the database. It loops through the array of IDs and with each loop it executes the delete query.
     * * @param arrayDeletionSelection Holds the UserIDs that have been selected for deletion
     */
    public static void DeleteUsers(String[] arrayDeletionSelection) throws ConnectionManagerException {
        try {
		    Connection conn = getConnection();
            for (int i = 0; i < arrayDeletionSelection.length; i++) { //loops through the checked boxes for deletion
                PreparedStatement pstmt = conn.prepareStatement("DELETE FROM user WHERE UserID=?");
                int k = Integer.parseInt(arrayDeletionSelection[i]);
                pstmt.setInt(1, k);
                pstmt.executeUpdate();
                cleanup(conn, pstmt, null);
            }
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    /**
     * This method has a UserID passed into it via selectedID. This value is used to execute an SQL query which retrieves the needed values from that
     * record and returns them via the Map userDetails.To be used by the admin only.
     * * @param selectedID This holds the UserID value of the User that has been selected to be edited.
     *
     * @param userDetails This maps the the labels with the relevant details from the database.
     * @return userDetails
     */
    public static Map AdminEditUser(String selectedID, Map userDetails) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE UserID=?");
            int k = Integer.parseInt(selectedID);
            pstmt.setInt(1, k);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userDetails.put("UserID", rs.getInt("UserID"));
                userDetails.put("Username", rs.getString("Username"));
                userDetails.put("Fname", rs.getString("Fname"));
                userDetails.put("Lname", rs.getString("Lname"));
                userDetails.put("email", rs.getString("email"));
                userDetails.put("Admin", rs.getString("Type"));
            }
            cleanup(conn, pstmt, rs);
            return userDetails;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    /**
     * This method takes in the new values that have requested to be updated in the database and performs the necessary SQL query to
     * perform this task.
     * * @param user Holds the new values that will be inserted into the relevant user record.
     */
    public static void AdminUpdateUser(User user) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE user set Username=?, Fname=?, Lname=?, email=?, Admin=? WHERE UserID=?");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFname());
            pstmt.setString(3, user.getLname());
            pstmt.setString(4, user.getemail());
            pstmt.setInt(5, user.getAdmin());
            pstmt.setInt(6, user.getUserID());
            pstmt.executeUpdate();
            cleanup(conn, pstmt, null);
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    /**
     * Checks if a user exists in the database given a UserID
     * * @param UserID Holds the value of the UserID passed in from the calling action.
     *
     * @return true if the user exists. false if the user does not exist.
     */
    public static boolean checkUserExists(String UserID) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            // Query returning a user with matching username
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM User WHERE UserID = ?");
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
            boolean exists = rs.next();
            cleanup(conn, pstmt, rs);
            return exists;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    /**
     * This is used to retrieve the details of a user given a username. This is only to be used by the customer user.
     *
     * @param username    Holds the value of the username whose details we wish to retrieve.
     * @param userDetails This maps the the labels with the relevant details from the database.
     * @return
     */
    public static Map UserEdit(String username, Map userDetails) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE Username=?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userDetails.put("Username", rs.getString("Username"));
                userDetails.put("Password", rs.getString("Password"));
                userDetails.put("Fname", rs.getString("Fname"));
                userDetails.put("Lname", rs.getString("Lname"));
                userDetails.put("email", rs.getString("email"));
            }
            cleanup(conn, pstmt, rs);
            return userDetails;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

/**
 * Gets the values held in the editing fields and update the database with them.
 * @param user holds the user that is being updated
 * @throws ConnectionManagerException
 */
    public static void UserUpdate(User user) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE user set Password=?, Fname=?, Lname=?, email=? WHERE Username=?");
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getFname());
            pstmt.setString(3, user.getLname());
            pstmt.setString(4, user.getemail());
            pstmt.setString(5, user.getUsername());
            pstmt.executeUpdate();
            cleanup(conn, pstmt, null);
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    //CHANGE THIS ONCE JDBC FIXED
    public static boolean checkBusinessCredentials(String userName, String password) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            // Query returning a user with matching username and password.
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM venue WHERE Name = ? and Password = ?");
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            boolean exists = rs.next();
            cleanup(conn, pstmt, rs);
            return exists;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

	/**
	 * Retrieves the password of a user given the username.
	 * @param userName userName is passed in to get the relevant password.
	 * @return the password linked to the username being used to try to login. If the username does not exist it returns fail
	 * @throws ConnectionManagerException
	 */
	public static String getPassword(String userName) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM User WHERE userName = ?");
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            String password = "fail";
            if (rs.next())
                password = rs.getString("password");

            cleanup(conn, pstmt, rs);
            return password;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
	}

	/**
	 * Given an email address checks that it does not already exist in the database.
	 * @param email email to be checked by function
	 * @return true if the email exists otherwise false
	 * @throws ConnectionManagerException
	 */
	public static boolean checkEmail(String email) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            // Query returning a user with matching username
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM User WHERE Email = ?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            boolean exists = rs.next();
            cleanup(conn, pstmt, rs);
            return exists;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
	}

    /**
	 * Functions gets user matching provided pattern. For every element in fields, there should be a
     * corresponding value in values, what will result in a query following below pattern:
     * SELECT * FROM User WHERE fields[0] = values[0] AND fields[1] = values[1]...
     * Ex 1.  userNew = ConnectionManager.getUserBy(new String[]{"Username"}, new Object[]{"Matt"});
     * Ex 2.  userNew = ConnectionManager.getUserBy(new String[]{"UserID"}, new Object[]{3});
	 * @param fields array of fields
     * @param values array of values
	 * @return User object if found, null otherwise
	 * @throws ConnectionManagerException
	 */
    public static UserNew getUserBy(String[] fields, Object[] values) throws ConnectionManagerException {
        if (fields.length != values.length)
            throw new ConnectionManagerException("getUserBy: fields and values must contain the same number of objects.")

        //Building the query.
        //String concatenation for fields is safe, as they are never provided by user.
        StringBuilder query = new StringBuilder("SELECT * FROM User WHERE " + fields[0] + " = ? ");
        for (int i = 1; i < fields.length; i++)
            query.append(" AND " + fields[i] + " = ? ");

        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement(query.toString());
            //Safe because field is always hardcoded in calling function.
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof String)
                    pstmt.setString(i + 1, (String)values[i]);
                else if (values[i] instanceof Integer)
                    pstmt.setInt(i + 1, (Integer)values[i]);
                else
                    throw new ConnectionManagerException("getUserBy: value which is neither String nor Integer.");
            }
            ResultSet rs = pstmt.executeQuery();
            UserNew user = null;
            if (rs.next())
                user = new UserNew(rs.getInt("UserID"),
                        rs.getInt("Type"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
            			rs.getString("Email"),
                        rs.getString("Username"),
                        rs.getString("Password"));

            cleanup(conn, pstmt, rs);
            return user;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }
}
