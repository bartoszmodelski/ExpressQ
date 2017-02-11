package com.delta.expressq.database;

import java.sql.*;
//Remove this if we don't need BigInteger or BigDecimal support
import java.math.*;

public class ConnectionManager {
	// JDBC driver name and database URL
	// CHANGE FOR DEPLOYMENT
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/ExpressQ";
	// Database credentials
	private static final String DB_USER = "root";
	private static final String DB_PASS = "";
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
			pstmt = conn.prepareStatement("SELECT * FROM user WHERE Username = ? and Password = ?");
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
			System.out.println("SQL query failed.");
		}
		return false;
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
	public static Transaction getTransaction(int transactionID) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM Transaction WHERE TransactionID = ?");
			pstmt.setInt(1, transactionID);
			ResultSet rs = pstmt.executeQuery();
			// Check whether a transaction was returned.
			while (rs.next()) {
				int userID = rs.getInt("UserID");
				int venueID = rs.getInt("VenueID");
				int listID = rs.getInt("ListID");
				float total = rs.getFloat("TotalPrice");
				String date = rs.getTimestamp("Time").toString();
				Transaction rTransaction = new Transaction(transactionID, userID, venueID, listID, total, date);
				rs.close();
				conn.close();
				return rTransaction;
			}
		} catch (SQLException se) {
			System.out.println("SQL query failed.");
		}
		return null;
	}
}
