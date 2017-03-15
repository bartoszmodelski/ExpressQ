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
    private static Connection conn = getConnection();

    // Method controlling connections to the database
    private static Connection getConnection() {		
        try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			conn.setAutoCommit(true);
        } catch (SQLException sqle) {
            System.out.println("Couldn't connect to database.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return conn;
    }

// Add APIKey parameter later

    /**
     * Checks if the login details provided by the user are correct.
     *
     * @param username The username to be checked
     * @param password The password to be checked
     * @return true if the username and password exist in the same record. false if they do not.
     */
    public static boolean checkCredentials(String username, String password) throws ConnectionManagerException {//NO LONGER USED

        PreparedStatement pstmt;
        try {
			conn = getConnection();
            // Query returning a user with matching username and password.
            pstmt = conn.prepareStatement("SELECT * FROM User WHERE Username = ? and Password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
            throw new ConnectionManagerException(sqle);
        }
        return false;
    }

    public static String getUsername(int userID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Username FROM User WHERE UserID = ?");
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("Username");
                rs.close();
                return username;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return "";
    }

    // Gets a transaction and returns it as a transaction object.
    public static Transaction getTransaction(String name, String APIpass, int transactionID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement("SELECT User.Username, TransactionID, User.UserID, Keywords, Transaction.VenueID, TotalPrice, Time, CollectionTime, Status "
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
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                int venueID = rs.getInt("VenueID");
                float total = rs.getFloat("TotalPrice");
                String date = rs.getTimestamp("Time").toString();
                int status = rs.getInt("Status");
				Time collection = rs.getTime("CollectionTime");
				String keywords = rs.getString("Keywords");
				String username = rs.getString("Username");
                Transaction transaction = new Transaction(transactionID, userID, venueID, total, date, status, keywords, collection, username);
                rs.close();
                return transaction;
            }
        } catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
            throw new ConnectionManagerException(sqle);
        }
        return null;
    }
	
	public static List<Integer> getIDsOfUpcomingTransactions(String name, String APIpass, int minutes) throws ConnectionManagerException {
		PreparedStatement pstmt;
		List<Integer> ids = new ArrayList<Integer>();
        try {
			conn = getConnection();
            pstmt = conn.prepareStatement("SELECT TransactionID " 
                    + " FROM Transaction, Venue " 
                    + " WHERE Venue.VenueID = Transaction.VenueID " 
					+ " AND CAST(Transaction.Time AS DATE) = CAST(CURRENT_TIMESTAMP AS DATE) " 
					+ " AND CollectionTime < CAST((DATE_ADD(CURRENT_TIMESTAMP, INTERVAL ? minute)) AS TIME) " 
					+ " AND Status = 0 " 
                    + " AND Venue.Name =  ? " 
                    + " AND Venue.APIpass =  ?");
            pstmt.setInt(1, minutes);
            pstmt.setString(2, name);
            pstmt.setString(3, APIpass);
            ResultSet rs = pstmt.executeQuery();
			
            while (rs.next()) {
                ids.add(rs.getInt("TransactionID"));         
            }
			rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return ids;
	}

    public static HashMap getItemsInTransaction(int transactionID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        try {
			
				conn = getConnection();
            pstmt = conn.prepareStatement("SELECT Name, Quantity "
                    + "FROM ItemQuantity, Item "
                    + "WHERE ItemQuantity.TransactionId = ? "
                    + "AND ItemQuantity.ItemID = Item.ItemID ");
            pstmt.setInt(1, transactionID);
            System.out.println(transactionID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                hmap.put(rs.getString("Name"), rs.getInt("Quantity"));
            }
            rs.close();

            return hmap;

        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        //return null;
    }

    public static boolean checkUserName(String username) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			conn = getConnection();
            // Query returning a user with matching username
            pstmt = conn.prepareStatement("SELECT * FROM User WHERE Username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return false;
    }

    public static boolean writeUser(String username, String password, String email, String fname, String lname) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			conn = getConnection();
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
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return false;
    }

    // Get the venueID
    public static int getVenueID(String name) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM Venue WHERE Name = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int venueID = rs.getInt("VenueID");
                rs.close();
                return venueID;
            }
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return 0;
    }


    public static void setItems(Map<String, Map<String, ArrayList<Item>>> items, String venueID) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			conn = getConnection();
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
            rs.close();

        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    public static void setVenues(Map<String, Integer> venues) throws ConnectionManagerException {
        Statement stmt;
        try {
			conn = getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name, VenueID FROM Venue");
            while (rs.next()) {
                venues.put(rs.getString("Name"), rs.getInt("VenueID"));
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
    }

    public static ArrayList<Item> getItemsByIDs(List<Integer> ids) {
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
			conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query.toString());
            for (int i = 0; i < ids.size(); i++) {
                pstmt.setInt(i + 1, ids.get(i));
            }
            //System.out.println(pstmt);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
                items.add(new Item(rs.getDouble("Price"), rs.getString("Name"), rs.getInt("ID")));

        } catch (SQLException sqle) {
            System.out.println("SQL query failed: " + sqle.getMessage());
        }

        return items;
    }


    public static int newOrder(String user, Order order) throws ConnectionManagerException {
        PreparedStatement pstmt;
        int transactionID = -1;
        try {
			
				conn = getConnection();
            conn.setAutoCommit(false);

            //add transaction
            pstmt = conn.prepareStatement("INSERT into transaction (UserID, VenueID, TotalPrice, Status, CollectionTime, Keywords) "
                            + " VALUES ((SELECT UserID FROM user WHERE Username = ?), ?, ?, 0, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user);
            pstmt.setInt(2, order.getVenue());
            pstmt.setDouble(3, order.getTotal());
            pstmt.setString(4, order.getCollectionTime());
            pstmt.setString(5, order.getKeywords());
            pstmt.executeUpdate();

            //Obtain transaction id
            ResultSet tableKeys = pstmt.getGeneratedKeys();
            tableKeys.next();
            transactionID = tableKeys.getInt(1);

            //add every item from order
            for (Map.Entry<Item, Integer> entry : order.getItemsAndQuantities().entrySet()) {
                pstmt = conn.prepareStatement("INSERT INTO `heroku_ce661b81b9c9192`.`itemquantity` (`ListID`, `ItemID`, `Quantity`, `TransactionID`) "
                        + "VALUES (NULL, ?, ?, ?)");
                pstmt.setInt(1, entry.getKey().getID());
                pstmt.setInt(2, entry.getValue());
                pstmt.setInt(3, transactionID);
                pstmt.executeUpdate();
                System.out.println("In here: " + Integer.toString(transactionID) + ", ID: " + Integer.toString(entry.getKey().getID()));
            }

            //commit transaction (database transaction, not financial)
            conn.commit();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
        return transactionID;

    }

    /**
     * This retrieves the data from the user table and passes it back into AdminAction so all the users' details can be displayed on screen
     *
     * @param users List that holds the values of each record in the user table.
     * @return users is returned if the query is successful, otherwise it returns null.
     */
    public static List DisplayUsers(List users) throws ConnectionManagerException {
        Statement stmt;
        try {
			
				conn = getConnection();
            stmt = conn.createStatement();
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
                user.setAdmin(rs.getInt("Admin"));
                users.add(user);
            }
            return users;
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        //return null;
    }

    /**
     * This method receives an array of UserIDs that have been chosen to be deleted from the database. It loops through the array of IDs and with each loop it executes the delete query.
     * * @param arrayDeletionSelection Holds the UserIDs that have been selected for deletion
     */
    public static void DeleteUsers(String[] arrayDeletionSelection) throws ConnectionManagerException {

        PreparedStatement stmt;
        try {
			
				conn = getConnection();
            for (int i = 0; i < arrayDeletionSelection.length; i++) { //loops through the checked boxes for deletion
                stmt = conn.prepareStatement("DELETE FROM user WHERE UserID=?");
                int k = Integer.parseInt(arrayDeletionSelection[i]);
                stmt.setInt(1, k);
                stmt.executeUpdate();
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

        PreparedStatement stmt;
        try {
			
				conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM user WHERE UserID=?");
            int k = Integer.parseInt(selectedID);
            stmt.setInt(1, k);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userDetails.put("UserID", rs.getInt("UserID"));
                userDetails.put("Username", rs.getString("Username"));
                userDetails.put("Fname", rs.getString("Fname"));
                userDetails.put("Lname", rs.getString("Lname"));
                userDetails.put("email", rs.getString("email"));
                userDetails.put("Admin", rs.getString("Admin"));
            }
            stmt.close();
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
        return userDetails;
    }

    /**
     * This method takes in the new values that have requested to be updated in the database and performs the necessary SQL query to
     * perform this task.
     * * @param user Holds the new values that will be inserted into the relevant user record.
     */
    public static void AdminUpdateUser(User user) throws ConnectionManagerException {

        PreparedStatement stmt;
        try {
			conn = getConnection();
            stmt = conn.prepareStatement("UPDATE user set Username=?, Fname=?, Lname=?, email=?, Admin=? WHERE UserID=?");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFname());
            stmt.setString(3, user.getLname());
            stmt.setString(4, user.getemail());
            stmt.setInt(5, user.getAdmin());
            stmt.setInt(6, user.getUserID());
            stmt.executeUpdate();
            stmt.close();

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

        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			
				conn = getConnection();
            // Query returning a user with matching username
            pstmt = conn.prepareStatement("SELECT * FROM User WHERE UserID = ?");
            pstmt.setString(1, UserID);
            ResultSet rs = pstmt.executeQuery();
			
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();

        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return false;
    }

    /**
     * This is used to retrieve the details of a user given a username. This is only to be used by the customer user.
     *
     * @param username    Holds the value of the username whose details we wish to retrieve.
     * @param userDetails This maps the the labels with the relevant details from the database.
     * @return
     */
    public static Map UserEdit(String username, Map userDetails) throws ConnectionManagerException {
        PreparedStatement stmt;
        try {
			
				conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM user WHERE Username=?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userDetails.put("Username", rs.getString("Username"));
                userDetails.put("Password", rs.getString("Password"));
                userDetails.put("Fname", rs.getString("Fname"));
                userDetails.put("Lname", rs.getString("Lname"));
                userDetails.put("email", rs.getString("email"));
            }
            stmt.close();
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
        return userDetails;
    }

    public static void UserUpdate(User user) throws ConnectionManagerException {
        PreparedStatement stmt;
        try {
			
				conn = getConnection();
            stmt = conn.prepareStatement("UPDATE user set Password=?, Fname=?, Lname=?, email=? WHERE Username=?");
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getFname());
            stmt.setString(3, user.getLname());
            stmt.setString(4, user.getemail());
            stmt.setString(5, user.getUsername());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }

    //CHANGE THIS ONCE JDBC FIXED
    public static boolean checkBusinessCredentials(String userName, String password) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			
				conn = getConnection();
            // Query returning a user with matching username and password.
            pstmt = conn.prepareStatement("SELECT * FROM venue WHERE Name = ? and Password = ?");
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return false;
    }
	/**
	 * Retrieves the password of a user given the username.
	 * @param userName userName is passed in to get the relevant password.
	 * @return the password linked to the username being used to try to login. If the username does not exist it returns fail
	 * @throws ConnectionManagerException
	 */
	public static String getPassword(String userName) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			
				conn = getConnection();
            pstmt = conn.prepareStatement("SELECT password FROM User WHERE userName = ?");
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                rs.close();
                return password;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return "fail";
	}

	public static boolean checkEmail(String email) throws ConnectionManagerException {
        // Using prepared statement to prevent SQL injection
        PreparedStatement pstmt;
        try {
			conn = getConnection();
            // Query returning a user with matching username
            pstmt = conn.prepareStatement("SELECT * FROM User WHERE Email = ?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a matching user was returned.
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return false;
	}
	
	public static boolean writeVenue(String name, String password, String address, String phonenumber) throws ConnectionManagerException {
		System.out.println(name+password+address+phonenumber);
        PreparedStatement pstmt;
        try {
        	conn = getConnection();
        	pstmt = conn.prepareStatement("INSERT into venue (name, address, phonenumber, acceptingorders, apipass, password) VALUES (?,?,?,?,?,?)");//, Statement.RETURN_GENERATED_KEYS);
        	pstmt.setString(1, name);
        	pstmt.setString(2, address);
        	pstmt.setString(3, phonenumber);
        	pstmt.setInt(4, 1);
        	pstmt.setString(5, name + "pass");
        	pstmt.setString(6, password);
            pstmt.executeUpdate();
        	//ResultSet tableKeys = pstmt.getGeneratedKeys();
            //tableKeys.next();
            //int autoGeneratedID = tableKeys.getInt(1);
        	System.out.println(pstmt);
        }catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
		return false;
	}
	
	public static String getBusinessPassword(String name) throws ConnectionManagerException {
        PreparedStatement pstmt;
        try {
			conn = getConnection();
            pstmt = conn.prepareStatement("SELECT password FROM Venue WHERE name = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                rs.close();
                return password;
            }
            rs.close();
        } catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
        return "fail";
	}

	public static boolean InsertMenu(int venueid, String name, String description) throws ConnectionManagerException{
		PreparedStatement pstmt;
        try {
			conn = getConnection();
			pstmt = conn.prepareStatement("INSERT into Menu (venueid, name, description) VALUES (?,?,?)");
			pstmt.setInt(1, venueid);
			pstmt.setString(2, name);
			pstmt.setString(3, description);
			pstmt.executeUpdate();
        }catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
        }
		return false;
	}

	public static void setMenus(Map<String, Integer> menus, int venueID) throws ConnectionManagerException {
		PreparedStatement pstmt;
		try {
			conn = getConnection();
            pstmt = conn.prepareStatement("SELECT name, menuid FROM menu WHERE venueid = ?");
            pstmt.setInt(1, venueID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                menus.put(rs.getString("name"), rs.getInt("menuid"));
            }
            rs.close();
		}catch (SQLException sqle) {
            throw new ConnectionManagerException(sqle);
		}
	}

    public static void DeleteMenus(String[] arrayDeletionSelection) throws ConnectionManagerException {
        PreparedStatement stmt;
        try {
			conn = getConnection();
            for (int i = 0; i < arrayDeletionSelection.length; i++) { //loops through the checked boxes for deletion
                stmt = conn.prepareStatement("DELETE FROM menu WHERE menuid=?");
                int k = Integer.parseInt(arrayDeletionSelection[i]);
                System.out.println(k);
                stmt.setInt(1, k);
                stmt.executeUpdate();
            }
        } catch (Exception ex) {
            throw new ConnectionManagerException(ex);
        }
    }
}
