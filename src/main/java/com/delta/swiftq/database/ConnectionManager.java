package com.delta.swiftq.database;

import java.sql.*;
import java.util.*;
import java.sql.Time;

import com.delta.swiftq.util.*;

public class ConnectionManager {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/heroku_ce661b81b9c9192";
    // Database credentials
    private static final String DB_USER = "b02576368bd1b5";
    private static final String DB_PASS = "6d1d4ae1";

    //Types of groupings (used in analytics)
    public static final int perWeek = 0;
    public static final int perMonth = 1;


    /**
     * This method controls connections to the database
     * @return conn if connection is successful, otherwise return an error message.
     * @throws ConnectionManagerException
     */
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

    /**
     * Close's connections to database when method is finished
     * @param conn
     * @param pstmt
     * @param rs
     */
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

    /**
     * Returns a username from the database given a UserID.
     * @param userID The ID we want to get the username for.
     * @return The username retrieved from the database.
     * @throws ConnectionManagerException
     */
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
	*	@return 	details of needed transaction as transaction object
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
 	*	Method checks if provided mobile credentials are valid.
 	*	@param		name 			venue name
 	*	@param		APIpass 		venue password for mobile application
 	*/
    public static boolean checkMobileCredentials(String name, String APIpass) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * "
                    + "FROM Venue "
                    + "WHERE Venue.Name =  ? "
                    + "AND Venue.APIpass =  ? ");
            pstmt.setString(1, name);
            pstmt.setString(2, APIpass);
            ResultSet rs = pstmt.executeQuery();
            // Check whether an account was returned.
            boolean exists = rs.next();
            cleanup(conn, pstmt, rs);
            return exists;

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            throw new ConnectionManagerException(sqle);
        }
    }

    /**
     *	Method updates status of specified transaction.
     *	@param 		transactionID 	id of needed transaction
     *	@param		name 			venue name
     *	@param		APIpass 		venue password for mobile application
     *	@param	    status 		    new status
     */
     public static void updateTransactionStatus(String name, String APIpass, int transactionID, int status)
                    throws ConnectionManagerException {
         try {
             Connection conn = getConnection();

             //Yes, does not make sense. No, won't work if single nested.
             PreparedStatement pstmt = conn.prepareStatement("UPDATE Transaction SET Transaction.Status = ? "
                    + "WHERE TransactionID in "
                        + "(SELECT * FROM "
                            + "(SELECT TransactionID "
                            + "FROM Transaction, Venue "
                            + "WHERE TransactionID = ? AND Venue.VenueID = Transaction.VenueID "
                            + "AND Venue.Name = ? AND Venue.APIpass =  ?)"
                        + "as X)");

             pstmt.setInt(1, status);
             pstmt.setInt(2, transactionID);
             pstmt.setString(3, name);
             pstmt.setString(4, APIpass);
             pstmt.executeUpdate();

             cleanup(conn, pstmt, null);
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
	*	@return 	list of ids (integers)
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
     *	Method gets those Order objects from database, which will be picked up within 'minutes' number of mintues.
     *	@param 		minutes 	    scope of upcomingness
     *	@param		name 			venue name
     *	@param		APIpass 		venue password for mobile application
     *	@return 	details of needed transaction as transaction object
     */
     public static Map<Transaction, Map<Item, Integer>> getUpcomingOrders(String name, String APIpass, int minutes) throws ConnectionManagerException {
         try {
             minutes = minutes + 60;
             Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT User.Username, User.Fname, User.Lname, TransactionID, "
                     + " User.UserID, Keywords, Transaction.VenueID, TotalPrice, Time, CollectionTime, Status "
                     + " FROM Transaction, Venue, User "
                     + " WHERE Venue.VenueID = Transaction.VenueID "
                     + " AND Venue.Name =  ? "
                     + " AND Venue.APIpass =  ? "
                     + " AND Transaction.UserID = User.UserID "
                     + " AND CAST(Transaction.Time AS DATE) = CAST(CURRENT_TIMESTAMP AS DATE) "
                     + " AND CollectionTime < CAST((DATE_ADD(CURRENT_TIMESTAMP, INTERVAL ? minute)) AS TIME) "
                     + " AND Status = 0 "
                     + " AND CollectionTime <> CAST('00:00:00' AS TIME)"); //exclude ones with unset time
             pstmt.setString(1, name);
             pstmt.setString(2, APIpass);
             pstmt.setInt(3, minutes);
             ResultSet rs = pstmt.executeQuery();
             // Check whether a transaction was returned.
             Map<Transaction, Map<Item, Integer>> orders = new HashMap<Transaction, Map<Item, Integer>>();

             while (rs.next()) {

                 int userID = rs.getInt("UserID");
                 int transactionID = rs.getInt("TransactionID");
                 int venueID = rs.getInt("VenueID");
                 float total = rs.getFloat("TotalPrice");
                 String date = rs.getTimestamp("Time").toString();
                 int status = rs.getInt("Status");
                 String fullname = rs.getString("Fname") + " " + rs.getString("Lname");
                 Time collection = rs.getTime("CollectionTime");
                 String keywords = rs.getString("Keywords");
                 String username = rs.getString("Username");
                 orders.put(new Transaction(transactionID, userID, venueID, total, date, status, keywords, collection, username, fullname),
                                new HashMap<Item, Integer>());
             }

             for (Transaction transaction: orders.keySet()) {
                     pstmt = conn.prepareStatement("SELECT Name, Price, Quantity, PreparationTime, Allergens, Item.ItemID as ItemID "
                             + "FROM ItemQuantity, Item "
                             + "WHERE ItemQuantity.TransactionID = ? "
                             + "AND ItemQuantity.ItemID = Item.ItemID ");
                     pstmt.setInt(1, transaction.transactionID);
                     rs = pstmt.executeQuery();
                     while (rs.next()) {
                         orders.get(transaction).put(
                                new Item(rs.getDouble("price"), rs.getString("Name"), rs.getInt("ItemID"), rs.getInt("PreparationTime"), rs.getString("Allergens")),
                                rs.getInt("Quantity"));
                     }
             }

             cleanup(conn, pstmt, rs);
             return orders;

         } catch (SQLException sqle) {
             System.out.println(sqle.getMessage());
             throw new ConnectionManagerException(sqle);
         }
     }

	/**
	*	Method gets items and their quantities for given transaction.
	*	@param 		transactionID 	id of transaction
	*	@return 	hashmap of item and quantity
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

    /**
     * Given a String, checks if that string already exists in the User table as a username.
     * @param username String that will be checked by the query.
     * @return True if it does exist, false if it does not.
     * @throws ConnectionManagerException
     */
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
     *
     * @param i
     * @return
     * @throws ConnectionManagerException
     */
    public static int getVenueID(int i) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM Venue WHERE UserID = ?");
            pstmt.setInt(1, i);
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
    public static void setItems(Map<String, ArrayList<Item>> items, String venueID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Item.ItemID AS ItemID, Item.Name AS ItemName, Item.Price as ItemPrice, Section.SectionID, "
                    + " Section.Description AS SectionName "
                    + "FROM Section, Item "
                    + "WHERE Section.VenueID = ?"
                    + "AND Section.SectionID = Item.SectionID");
            pstmt.setString(1, venueID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String sectionName = rs.getString("SectionName");
                if (!items.containsKey(sectionName))
                    items.put(sectionName, new ArrayList<Item>());

                items.get(sectionName).add(
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
	*	@return 	list of Item objects
	*/
    public static ArrayList<Item> getItemsByIDs(List<Integer> ids) throws ConnectionManagerException {
        //to avoid sending unprepared statements (query had one hardcoded Item)
        ArrayList<Item> items = new ArrayList<Item>();
        if (ids.size() == 0)
            return items;

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
	*	@return 	id of just added transaction
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
                user.setType(rs.getInt("Type"));
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
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM user WHERE UserID=?");
            for (int i = 0; i < arrayDeletionSelection.length; i++) { //loops through the checked boxes for deletion
                int k = Integer.parseInt(arrayDeletionSelection[i]);
                pstmt.setInt(1, k);
                pstmt.executeUpdate();
            }
            cleanup(conn, pstmt, null);
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
                userDetails.put("Type", rs.getString("Type"));
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
            PreparedStatement pstmt = conn.prepareStatement("UPDATE user set Username=?, Fname=?, Lname=?, email=?, Type=? WHERE UserID=?");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFname());
            pstmt.setString(3, user.getLname());
            pstmt.setString(4, user.getemail());
            pstmt.setInt(5, user.getType());
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

            String password = null;
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
    private static UserNew getUserBy(String[] fields, Object[] values) throws ConnectionManagerException {
        if (fields.length != values.length)
            throw new ConnectionManagerException("getUserBy: fields and values must contain the same number of objects.");

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

    /**
     * Returns user object corresponding to given username
     *
     * @param username	sought user's username
     * @return			user object if found, null otherwise
     * @throws ConnectionManagerException
     */
    public static UserNew getUserByUsername(String username) throws ConnectionManagerException {
        return getUserBy(new String[]{"Username"}, new Object[]{username});
    }

	public static String getVenueName(String id) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            // Query returning a user with matching username
            PreparedStatement pstmt = conn.prepareStatement("SELECT name FROM Venue WHERE venueid = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            String name = null;
            // Check whether a matching user was returned.
            if (rs.next())
            	name = rs.getString("name");
            cleanup(conn, pstmt, rs);
            return name;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
	}


	/**
	 * Returns a map of section descriptions and their IDs given a venueID.
	 * @param sections The map containing the details of the sections.
	 * @param venueID The venueID that will be used to retrieve the desired sections.
	 * @throws ConnectionManagerException
	 */
	public static void setSections(Map<String, Integer> sections, int venueID) throws ConnectionManagerException {
		PreparedStatement pstmt;
		try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT description, sectionid FROM section WHERE venueid = ?");
            pstmt.setInt(1, venueID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sections.put(rs.getString("description"), rs.getInt("sectionid"));
            }
            cleanup(conn, null, rs);
		}catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
		}
	}

	/**
	 * Creates a new section with the provided description and venueID. SectionID is auto incremented.
	 * @param venueid the venue that the new section will belong to.
	 * @param description the name of the new section.
	 * @return false if the section could not be inserted.
	 * @throws ConnectionManagerException
	 */
	public static boolean InsertSection(int venueid, String description) throws ConnectionManagerException{
		PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement("INSERT into section (venueid, description) VALUES (?,?)");
			pstmt.setInt(1, venueid);
			pstmt.setString(2, description);
			pstmt.executeUpdate();
			cleanup (conn, pstmt, null);
        }catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
		return false;
	}

	/**
	 * Deletes sections from database.
	 * @param arraySectionDeleteSelection list of sectionIDs to be deleted.
	 * @throws ConnectionManagerException
	 */
	public static void DeleteSections(String[] arraySectionDeleteSelection) throws ConnectionManagerException {
        PreparedStatement stmt;
        try {
			Connection conn = getConnection();
            for (int i = 0; i < arraySectionDeleteSelection.length; i++) { //loops through the checked boxes for deletion
                stmt = conn.prepareStatement("DELETE FROM section WHERE sectionid=?");
                int k = Integer.parseInt(arraySectionDeleteSelection[i]);
                stmt.setInt(1, k);
                stmt.executeUpdate();
            }
            cleanup(conn, null, null);
        } catch (Exception ex) {
        	throw new ConnectionManagerException(ex);
        }
	}

	/**
	 * Returns the current description of a section given its id.
	 * @param sectionID id of section to be edited.
	 * @param description current description of section.
	 * @return Current description retrieved from database.
	 * @throws ConnectionManagerException
	 */
	public static String EditSection(String sectionID, String description) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT description FROM section WHERE sectionID=?");
            pstmt.setString(1, sectionID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	description = rs.getString("description");
            }
            cleanup(conn, pstmt, rs);
            return description;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
	}

	/**
	 * Updates a section with it's new name.
	 * @param newName The new value held in the description part of the section table.
	 * @param sectionID the ID of the section that is being updated.
	 * @throws ConnectionManagerException
	 */
	public static void UpdateSection(String newName, String sectionID) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE section set description=?  WHERE sectionid=?");
            pstmt.setString(1, newName);
            pstmt.setString(2, sectionID);
            pstmt.executeUpdate();
            cleanup(conn, pstmt, null);
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

	/**
	 * Returns a map of item name and their IDs given a sectionID.
	 * @param items The map containing the details of the items.
	 * @param sectionID The sectionID that will be used to retrieve the desired items.
	 * @throws ConnectionManagerException
	 */

	public static void getItemsBySection(Map<String, Integer> items, String sectionID) throws ConnectionManagerException {
		PreparedStatement pstmt;
		try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT name, itemid FROM item WHERE sectionid = ?");
            pstmt.setString(1, sectionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                items.put(rs.getString("name"), rs.getInt("itemid"));
            }
            cleanup(conn, null, rs);
		}catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
		}
	}

	/**
	 * Creates a new item in the database given the new values to be entered.
	 * @param selectedSectionID
	 * @param price
	 * @param name
	 * @param itemdescription
	 * @param stock
	 * @param allergens
	 * @param preparationtime
	 * @return false if the item cannot be created.
	 * @throws ConnectionManagerException
	 */
	public static boolean InsertItem(String selectedSectionID, float price, String name, String itemdescription, int stock, String allergens, int preparationtime) throws ConnectionManagerException {
		PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
			pstmt = conn.prepareStatement("INSERT into item (sectionid, price, name, description, stock, allergens, preparationtime) VALUES (?,?,?,?,?,?,?)");
			pstmt.setString(1, selectedSectionID);
			pstmt.setFloat(2, price);
			pstmt.setString(3, name);
			pstmt.setString(4, itemdescription);
			pstmt.setInt(5, stock);
			pstmt.setString(6, allergens);
			pstmt.setInt(7, preparationtime);
			pstmt.executeUpdate();
			cleanup(conn, pstmt, null);
        }catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
		return false;
	}

	/**
	 * Deletes items from the database.
	 * @param arrayItemDeleteSelection list of itemIDs to be deleted.
	 * @throws ConnectionManagerException
	 */
	public static void DeleteItems(String[] arrayItemDeleteSelection) throws ConnectionManagerException {
        PreparedStatement stmt;
        try {
			Connection conn = getConnection();
            for (int i = 0; i < arrayItemDeleteSelection.length; i++) { //loops through the checked boxes for deletion
                stmt = conn.prepareStatement("DELETE FROM item WHERE itemid=?");
                int k = Integer.parseInt(arrayItemDeleteSelection[i]);
                stmt.setInt(1, k);
                stmt.executeUpdate();
            }
            cleanup(conn, null, null);
        } catch (Exception ex) {
        	throw new ConnectionManagerException(ex);
        }
	}

	/**
	 * Retrieves the current information of the item requested to be edited by the user.
	 * @param selectedItemID id of the item to be edited.
	 * @param itemDetails map of the item's current details
	 * @return The map 'itemDetails' containing all the information from the database.
	 * @throws ConnectionManagerException
	 */
	public static Map EditItem(String selectedItemID, Map itemDetails) throws ConnectionManagerException {
        try {
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM item WHERE itemid=?");
            int k = Integer.parseInt(selectedItemID);
            pstmt.setInt(1, k);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	itemDetails.put("ItemID", rs.getString("itemid"));
            	itemDetails.put("SectionID", rs.getString("SectionID"));
            	itemDetails.put("Price", rs.getString("Price"));
            	itemDetails.put("Name", rs.getString("Name"));
            	itemDetails.put("Description", rs.getString("Description"));
            	itemDetails.put("Stock", rs.getString("Stock"));
            	itemDetails.put("Allergens", rs.getString("Allergens"));
            	itemDetails.put("PreparationTime", rs.getString("PreparationTime"));
            }
            cleanup(conn, pstmt, rs);
            return itemDetails;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
	}

	/**
	 * Updates an item with the new changes made by the user.
	 * @param itemID
	 * @param sectionID
	 * @param name
	 * @param description
	 * @param price
	 * @param stock
	 * @param allergens
	 * @param preparationtime
	 * @throws ConnectionManagerException
	 */
	public static void UpdateItem(int itemID, String sectionID, String name, String description, float price, int stock, String allergens, int preparationtime) throws ConnectionManagerException {
        try {
			Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE item set sectionID=?, name=?, description=?, price=?, stock=?, allergens=?, preparationtime=?  WHERE itemid=?");
            pstmt.setString(1, sectionID);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setFloat(4, price);
            pstmt.setInt(5, stock);
            pstmt.setString(6, allergens);
            pstmt.setInt(7, preparationtime);
            pstmt.setInt(8, itemID);
            pstmt.executeUpdate();
            cleanup(conn, pstmt, null);
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }

	}

	/**
	 * Gets active user transactions given a userID
	 * @param orderDetails maps the needed details of the transactions.
	 * @param userID ID used to retrieve the desired transactions.
	 * @return populated orderDetails
	 * @throws ConnectionManagerException
	 */
	public static Map getUserTransactions(Map<String, String> orderDetails, int userID) throws ConnectionManagerException {
		PreparedStatement pstmt;
		try{
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT transactionid, totalprice from transaction  WHERE userid=? AND status=0");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
            	orderDetails.put(rs.getString("transactionID"), rs.getString("totalprice"));
            }
            cleanup(conn, pstmt, rs);
            return orderDetails;
		}catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
	}

	public static boolean adminWriteUser(String username, String password, String email, String fName, String lName, int type) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			Connection conn = getConnection();
            pstmt = conn.prepareStatement("INSERT into User (Fname, Lname, email, Username, Password, Type) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.setInt(6, type);
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

    private static String parseGroupBy(int grouping)
            throws ConnectionManagerException {
        if (grouping == perWeek) {
            return "week";
        } else if (grouping == perMonth) {
            return "month";
        } else {
            throw new ConnectionManagerException("Unknown grouping type. Please use constants defined in CM.");
        }
    }

    /**
	 * Gets overall AverageCustomerSpending per month or week.
	 * @param venueOwnerID ID of user account with assigned venue
	 * @param grouping grouping perMonth or perWeek
	 * @param year year from which data should be retrieved
     * @return pairs of ACS / month or ACS / week number
	 * @throws ConnectionManagerException
	 */
    public static Map<Integer, Integer> getACS(int venueOwnerID, int grouping, int year)
            throws ConnectionManagerException {
        PreparedStatement pstmt;
        Map ACSs = new HashMap<Integer, Integer>();
        String groupBy = parseGroupBy(grouping);
        try{
            Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT GROUP_CONCAT(DISTINCT Transaction.UserID) UserIDs, "
                        + "COUNT(DISTINCT Transaction.UserID) as noCustomers, SUM(TotalPrice) as TCS, "
		                + "ROUND(SUM(TotalPrice) / COUNT(DISTINCT Transaction.UserID)) as ACS, COUNT(*), "
                        + "WEEK(time) as week, MONTH(time) as month "
                    + "FROM Transaction, Venue "
                    + "WHERE Transaction.VenueID = Venue.VenueID "
	                    + "AND Venue.UserID = ? "
	                    + "AND YEAR(time) = ? "
                    + "GROUP BY " + groupBy);

            pstmt.setInt(1, venueOwnerID);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                ACSs.put(rs.getInt(groupBy), rs.getInt("ACS"));
            }
            cleanup(conn, pstmt, rs);
            return ACSs;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    public static Map<java.sql.Date, Integer> getItemSale(int venueOwnerID, int itemID,
            java.sql.Date fromDate, java.sql.Date toDate)
            throws ConnectionManagerException {
        PreparedStatement pstmt;
        Map salePerDay = new HashMap<java.sql.Date, Integer>();
        try{
            Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT DATE(Transaction.Time) AS date, SUM( ItemQuantity.Quantity ) AS sum "
                    + " FROM ItemQuantity, Transaction, Venue "
                    + " WHERE Transaction.TransactionID = ItemQuantity.TransactionID "
	                       + " AND Transaction.VenueID = Venue.VenueID "
                           + " AND Venue.UserID = ? "
                           + " AND ItemQuantity.ItemID = ? "
                           + " AND DATE(Transaction.Time) BETWEEN ? AND ? "
                    + " GROUP BY DATE(Transaction.Time) "
                    + " ORDER BY Transaction.Time ");

            pstmt.setInt(1, venueOwnerID);
            pstmt.setInt(2, itemID);
            pstmt.setDate(3, fromDate);
            pstmt.setDate(4, toDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                salePerDay.put(rs.getDate("date"), rs.getInt("sum"));
            }
            cleanup(conn, pstmt, rs);
            return salePerDay;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    public static Map<String, Double> getAllItemsPopularity(int venueOwnerID,
            java.sql.Date fromDate, java.sql.Date toDate)
            throws ConnectionManagerException {
        PreparedStatement pstmt;
        Map popularities = new HashMap<String, Double>();
        try{
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(" SELECT * FROM "
                + " (SELECT COUNT(*) as noItems FROM Item, Section, Venue "
                    + " WHERE Item.SectionID = Section.SectionID AND Section.VenueID = Venue.VenueID AND Venue.UserID = ?) as count, " //userid

                + " (SELECT SUM( ItemQuantity.Quantity ) AS totalSale "
                    + " FROM ItemQuantity, Transaction, Venue "
                    + " WHERE Transaction.TransactionID = ItemQuantity.TransactionID "
                        + " AND Transaction.VenueID = Venue.VenueID "
                        + " AND Venue.UserID = ? " //userid
						+ " AND DATE(Transaction.Time) BETWEEN ? AND ?) as total, " //from, to

                + " (SELECT SUM( ItemQuantity.Quantity ) AS itemSale, Item.ItemID, Item.Name "
                    + " FROM ItemQuantity, Transaction, Venue, Item "
                    + " WHERE Transaction.TransactionID = ItemQuantity.TransactionID "
                        + " AND Transaction.VenueID = Venue.VenueID "
						+ " AND ItemQuantity.ItemID = Item.ItemID "
                        + " AND Venue.UserID = ? " //userid
						+ " AND DATE(Transaction.Time) BETWEEN ? AND ? " //from, to
	                + " GROUP BY ItemQuantity.ItemID) as itemRow "

	            + " GROUP BY itemRow.ItemID ");


            pstmt.setInt(1, venueOwnerID);
            pstmt.setInt(2, venueOwnerID);
            pstmt.setInt(5, venueOwnerID);

            pstmt.setDate(3, fromDate);
            pstmt.setDate(4, toDate);
            pstmt.setDate(6, fromDate);
            pstmt.setDate(7, toDate);
            ResultSet rs = pstmt.executeQuery();
            double popularity = 0;
            while (rs.next()){
                double itemSale = rs.getDouble("itemSale");
                double totalSale = rs.getDouble("totalSale");
                double noItems = rs.getInt("noItems");

                popularity = (itemSale / (totalSale / noItems));
                popularities.put(rs.getString("Name"), popularity);
            }
            cleanup(conn, pstmt, rs);
            return popularities;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }



    public static Map<String, Integer> getMostCommonlyPurchasedTogether(int venueOwnerID,
            java.sql.Date fromDate, java.sql.Date toDate)
            throws ConnectionManagerException {
        PreparedStatement pstmt;
        try{
            Map<String, Integer> pairAndShare = new HashMap<String, Integer>();
            Connection conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Transaction.TransactionID, iq1.ItemID, iq2.ItemID, "
                            + " i1.Name as name1, i2.Name as name2, COUNT(*) as count "
                        + " FROM Item as i1, Item as i2, Transaction, ItemQuantity as iq1, ItemQuantity as iq2, Venue  "
                        + " WHERE Transaction.VenueID = Venue.VenueID  "
                        	+ " AND Venue.UserID = ? "
                        	+ " AND iq1.TransactionID = Transaction.TransactionID  "
                        	+ " AND iq2.TransactionID = iq1.TransactionID  "
                        	+ " AND iq1.ItemID > iq2.ItemID  "
                        	+ " AND iq1.ItemID = i1.ItemID  "
                        	+ " AND iq2.ItemID = i2.ItemID "
                        	+ " AND DATE(Transaction.Time) BETWEEN ? AND ?  "
                        + " GROUP BY Concat(iq1.ItemID, \"-\", iq2.ItemID)  "
                        + " ORDER BY count DESC "); //fr

            pstmt.setInt(1, venueOwnerID);
            pstmt.setDate(2, fromDate);
            pstmt.setDate(3, toDate);
            ResultSet rs = pstmt.executeQuery();

            int rest = 0;
            int first7only = 0;
            while (rs.next()){
                int count = rs.getInt("count");

                if (first7only < 6) {
                    String item1 = rs.getString("name1");
                    String item2 = rs.getString("name2");
                    pairAndShare.put(item1 + ", " + item2, count);
                    first7only++;
                } else {
                    rest += count;
                }
            }
            pairAndShare.put("Rest", rest);
            cleanup(conn, pstmt, rs);
            return pairAndShare;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }

    }



    public static Map<Integer, Integer> getTransactionsPerHour(int venueOwnerID,
            java.sql.Date fromDate, java.sql.Date toDate)
            throws ConnectionManagerException {
        Map<Integer, Integer> transactionsPerHour = new HashMap<Integer, Integer>();
        PreparedStatement pstmt;
        try{
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(" SELECT hour(transaction.time) as hour, COUNT(*) as count  "
                + " FROM transaction, venue  "
                + " WHERE transaction.venueID = venue.venueID  "
    	            + " AND venue.UserID = ?  "
    	            + " AND DATE(Transaction.Time) BETWEEN ? AND ? "
                + " GROUP BY hour "
                + " ORDER BY hour "); //from, to


            pstmt.setInt(1, venueOwnerID);
            pstmt.setDate(2, fromDate);
            pstmt.setDate(3, toDate);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int hour = rs.getInt("hour");
                int transactions = rs.getInt("count");
                transactionsPerHour.put(hour, transactions);
            }
            cleanup(conn, pstmt, rs);
            return transactionsPerHour;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }


    public static Map<Integer, Double> getCustomerRetentionRate(int venueOwnerID, int year)
            throws ConnectionManagerException {
        Map<Integer, Double> CRRperMonth = new HashMap<Integer, Double>();
        PreparedStatement pstmt;
        try{
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(""
                + " SELECT Venue.VenueID INTO @var_venueID "
                + " FROM Venue "
                + " WHERE Venue.UserID = ?; " //userid

                + " SELECT ec_without_nc, sc, ec_without_nc / sc AS crr, q1.month, q2.u1month, q2.u2month "
                + " FROM ( "

                    + " SELECT COUNT( DISTINCT transaction.userID ) AS sc, MONTH( Transaction.Time ) AS MONTH  "
                    + " FROM transaction "
                    + " WHERE YEAR( Transaction.Time ) = ?" //yr
                        + " AND transaction.VenueID = @var_venueID  "
                    + " GROUP BY MONTH) AS q1, "

                    + " (SELECT COUNT( DISTINCT u1.UserID ) AS ec_without_nc, u1.month AS u1month, u2.month AS u2month "
                    + " FROM  "
                        + " (SELECT DISTINCT transaction.userID, MONTH( Transaction.Time ) AS  MONTH  "
                        + " FROM transaction "
                        + " WHERE YEAR( Transaction.Time ) = ? " //yr
                            + " AND transaction.VenueID = @var_venueID) AS u1, "

                        + " (SELECT DISTINCT transaction.userID, MONTH( Transaction.Time ) AS MONTH "
                        + " FROM transaction "
                        + " WHERE YEAR( Transaction.Time ) = ? " //yr
                            + " AND transaction.VenueID = @var_venueID) AS u2 "
                    + " WHERE u1.userID = u2.userID "
                        + " AND u1.month +1 = u2.month "
                    + " GROUP BY CONCAT( u1.month,  \"-\", u2.month )) AS q2 "
                + " WHERE q1.month = q2.u1month ");


            pstmt.setInt(1, venueOwnerID);
            pstmt.setInt(2, year);
            pstmt.setInt(3, year);
            pstmt.setInt(4, year);
            System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();

            double crr = 0;
            int month = 0;
            while (rs.next()){
                crr = rs.getDouble("crr");
                month = rs.getInt("u2month");
                CRRperMonth.put(month, crr);
            }
            cleanup(conn, pstmt, rs);
            return CRRperMonth;
        } catch (Exception ex) {
            System.out.println(ex);
            throw new ConnectionManagerException(ex);
        }
    }

    public static double getItemPopularity(int venueOwnerID, int itemID,
            java.sql.Date fromDate, java.sql.Date toDate)
            throws ConnectionManagerException {
        PreparedStatement pstmt;
        try{
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(" SELECT * FROM "
                        + " (SELECT COUNT(*) as noItems "
                            + " FROM Item, Section, Venue "
                            + " WHERE Item.SectionID = Section.SectionID "
                                + " AND Section.VenueID = Venue.VenueID "
                                + " AND Venue.UserID = ?) as count, " //userId

                        + " (SELECT SUM( ItemQuantity.Quantity ) AS totalSale  "
                            + " FROM ItemQuantity, Transaction, Venue  "
                            + " WHERE Transaction.TransactionID = ItemQuantity.TransactionID  "
                                + " AND Transaction.VenueID = Venue.VenueID  "
                                + " AND Venue.UserID = ? " //userId
						        + " AND DATE(Transaction.Time) BETWEEN ? AND ?) as total, " //from, to

                        + " (SELECT SUM( ItemQuantity.Quantity ) AS itemSale  "
                            + " FROM ItemQuantity, Transaction, Venue  "
                            + " WHERE Transaction.TransactionID = ItemQuantity.TransactionID  "
                                + " AND Transaction.VenueID = Venue.VenueID  "
                                + " AND Venue.UserID = ? " //userId
						        + " AND ItemQuantity.ItemId = ? " //itemID
						        + " AND DATE(Transaction.Time) BETWEEN ? AND ?) as item "); //from, to


            pstmt.setInt(1, venueOwnerID);
            pstmt.setInt(2, venueOwnerID);
            pstmt.setInt(5, venueOwnerID);

            pstmt.setDate(3, fromDate);
            pstmt.setDate(4, toDate);
            pstmt.setDate(7, fromDate);
            pstmt.setDate(8, toDate);

            pstmt.setInt(6, itemID);

            ResultSet rs = pstmt.executeQuery();

            double popularity = -1;
            if (rs.next()){
                double itemSale = rs.getDouble("itemSale");
                double totalSale = rs.getDouble("totalSale");
                double noItems = rs.getInt("noItems");

                popularity = (itemSale / (totalSale / noItems));
            } else {
                throw new Exception ("Result set empty");
            }
            cleanup(conn, pstmt, rs);
            return popularity;
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }


}
