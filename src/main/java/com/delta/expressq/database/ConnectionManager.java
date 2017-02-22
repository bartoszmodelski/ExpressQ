package com.delta.expressq.database;

import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import com.delta.expressq.util.*;

public class ConnectionManager {
	// JDBC driver name and database URL
	// CHANGE FOR DEPLOYMENT
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/heroku_ce661b81b9c9192";
	// Database credentials
	private static final String DB_USER = "b02576368bd1b5";
	private static final String DB_PASS = "6d1d4ae1";
	private static Connection conn = null;

	// Method controlling connections to the database
	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException se) {
			System.out.println("Couldn't connect to database.");
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found.");
		}
		return conn;
	}

	// Add APIKey parameter later
	public static boolean checkCredentials(String username, String password) {
		Connection conn = getConnection();
		// Using prepared statement to prevent SQL injection
		PreparedStatement pstmt = null;
		try {
			// Query returning a user with matching username and password.
			pstmt = conn.prepareStatement("SELECT * FROM User WHERE Username = ? and Password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			// Check whether a matching user was returned.
			if (rs.next()) {
				rs.close();
				conn.close();
				return true;
			}
			rs.close();
			conn.close();
		} catch (SQLException sqle) {
			System.out.println("SQL query failed: " + sqle.getMessage());
		}
		return false;
	}
	
	public static String getUsername(int userID) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT Username FROM User WHERE UserID = ?");
			pstmt.setInt(1, userID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String username = rs.getString("Username");
				rs.close();
				conn.close();
				return username;
			}
			rs.close();
			conn.close();
		} catch (SQLException sqle) {
			System.out.println("SQL query failed: " + sqle.getMessage());
		}
		return "";
	}
	
	// Gets a venue and returns it as a venue object.
    public static Venue getVenue(int venueID) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM Venue WHERE VenueID = ?");
            pstmt.setInt(1, venueID);
            ResultSet rs = pstmt.executeQuery();
            // Check whether a transaction was returned.
            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int phoneNumber = rs.getInt("PhoneNumber");
                boolean acceptingOrders = rs.getBoolean("AcceptingOrders");
                Venue rVenue = new Venue(venueID, name, address, phoneNumber, acceptingOrders);
                rs.close();
                conn.close();
                return rVenue;
            }
        } catch (SQLException se) {
            System.out.println("SQL query failed.");
        }
        return null;
    }

	// Gets a transaction and returns it as a transaction object.
	public static Transaction getTransaction(String name, String APIpass, int transactionID) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT TransactionID, UserID, Transaction.VenueID, TotalPrice, Time, Issued " 
													+ "FROM Transaction, Venue "
													+ "WHERE TransactionId = ? "
													+ "AND Venue.VenueID = Transaction.VenueID "
													+ "AND Venue.Name =  ? "
													+ "AND Venue.APIpass =  ? ");
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
				boolean issued = rs.getBoolean("Issued");
				Transaction rTransaction = new Transaction(transactionID, userID, venueID, total, date, issued);
				rs.close();
				conn.close();
				return rTransaction;
			}
		} catch (SQLException se) {
			System.out.println("SQL query failed. "+se.getMessage());
		}
		return null;
	}
	
	public static HashMap getItemsInTransaction(int transactionID) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		try {
			pstmt = conn.prepareStatement("SELECT Name, Quantity " 
													+ "FROM ItemQuantity, Item "
													+ "WHERE ItemQuantity.TransactionId = ? "
													+ "AND ItemQuantity.ItemID = Item.ItemID ");
			pstmt.setInt(1, transactionID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				hmap.put(rs.getString("Name"), rs.getInt("Quantity"));
			}
			rs.close();
			conn.close();
			
			return hmap;
			
		} catch (SQLException se) {
			System.out.println("SQL query failed. "+se.getMessage());
		}
		return null;
	}
	
	public static boolean checkUserName(String username) {
		  Connection conn = getConnection();
		  // Using prepared statement to prevent SQL injection
		  PreparedStatement pstmt = null;
		  try {
		    // Query returning a user with matching username
		    pstmt = conn.prepareStatement("SELECT * FROM User WHERE Username = ?");
		    pstmt.setString(1, username);
		    ResultSet rs = pstmt.executeQuery();
		    // Check whether a matching user was returned.
		    if (rs.next()) {
		      rs.close();
		      conn.close();
		      return true;
		    }
		    rs.close();
		    conn.close();
		  } catch (SQLException sqle) {
		    System.out.println("SQL query failed: " + sqle.getMessage());
		  }
		  return false;
		}
	
	public static boolean writeUser(String username, String password, String email, String fname, String lname ) {
		  Connection conn = getConnection();
		  // Using prepared statement to prevent SQL injection
		  PreparedStatement pstmt = null;
		  try {
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
		    conn.close();
		  } catch (SQLException sqle) {
		    System.out.println("SQL query failed: " + sqle.getMessage());
		  }
		  return false;
		}

	// Get the venueID
	public static int getVenueID(String name){
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM Venue WHERE Name = ?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int venueID = rs.getInt("VenueID");
				rs.close();
				return venueID;
			}
		} catch (SQLException se) {
			System.out.println("SQL query failed.");
		}
		return 0;
	}

	
	
	public static void setItems(Map<String, Map<String, ArrayList<Item>>> items, String venueID) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT Item.ItemID AS ItemID, Item.Name AS ItemName, Item.Price as ItemPrice, Section.SectionID, "
					+ " Section.Description AS SectionName, "
					+ " Menu.MenuID, Menu.VenueID, Menu.Description AS MenuName " 
					+ "FROM Menu, Section, Item " 
					+ "WHERE Menu.VenueID = ?" 
					+ "AND Menu.MenuID = Section.MenuID " 
					+ "AND Section.SectionID = Item.SectionID");
			pstmt.setString(1, venueID);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
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
			conn.close();
			
		} catch(SQLException sqle) {
			System.out.println("SQL query failed: " + sqle.getMessage());
		}
	}
	
	public static void setVenues(Map<String,Integer> venues){
		Connection conn = getConnection();
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Name, VenueID FROM Venue");
			while(rs.next()){
				venues.put(rs.getString("Name"), rs.getInt("VenueID"));
			}
			rs.close();
			conn.close();
		} catch(SQLException sqle) {
			System.out.println("SQL query failed: " + sqle.getMessage());
		}
	}
	
	public static ArrayList<Item> getItemsByIDs(List<Integer> ids) {
		//to avoid sending unprepared statements (query had one hardcoded Item)
		ArrayList<Item> items = new ArrayList<Item>();
		if (ids.size () == 0)
			return items;
		
		//System.out.println(ids.toString());
		
		Connection conn = getConnection();
		StringBuilder query = new StringBuilder("SELECT Item.ItemID as ID, Item.Name AS Name, Item.Price as Price FROM Item WHERE Item.ItemID = ? ");

		for (int i = 1; i < ids.size(); i++) {
			query.append("OR Item.ItemID = ? ");
		}
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			for (int i = 0; i < ids.size(); i++) {
				pstmt.setInt(i + 1, ids.get(i));
			}
			//System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
				items.add(new Item(rs.getDouble("Price"), rs.getString("Name"), rs.getInt("ID")));
			
		} catch(SQLException sqle) {
			System.out.println("SQL query failed: " + sqle.getMessage());
		} 
		
		return items;
	}
	
    public static int getLastTransactionID(){
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT *FROM `Transaction`ORDER BY `TransactionID` DESC LIMIT 0 , 1");
		ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int transactionID = rs.getInt("TransactionID");
				rs.close();
				return transactionID;
			}
		} catch (SQLException se) {
			System.out.println("SQL query failed.");
		}
		return 0;
	}
	
	public static void newOrder(String user, Order order) {		  
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("INSERT into transaction (UserID, VenueID, TotalPrice, Status) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		    pstmt.setString(1, fname);
		    pstmt.setInt(2, order.getVenue());
		    pstmt.setInt(3, 0);
		    pstmt.setInt(4, 0);
		    pstmt.executeUpdate();		    /*
		    ResultSet tableKeys = pstmt.getGeneratedKeys();
		    tableKeys.next();
		    int autoGeneratedID = tableKeys.getInt(1);*/		    
		    conn.close();
		} catch (SQLException sqle) {
		    System.out.println("SQL query failed: " + sqle.getMessage());
		}
	}
}

