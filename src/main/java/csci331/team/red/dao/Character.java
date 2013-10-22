package csci331.team.red.dao;

/*
 * create/return a character for the game
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;

public class Character {
	private static Connection connection = null;
	private static final String ID = "ID";
	private static final String FIRSTNAME = "firstName";
	private static final String LASTNAME = "lastName";
	private static final String PASSPORTID = "passportID";
	private static final String ADDRESS = "address";
	private static final String CITY = "city";
	private static final String POSTAL = "postal";
	private static final String COUNTRY = "country";
	private static final String OCCUPATION = "occupation";

	/*
	 * Open Database Connection
	 */
	public static boolean openConnection(String url) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = java.sql.DriverManager.getConnection(url);
		} catch (ClassNotFoundException cnfe) {
			System.err.println(cnfe.getMessage());
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			try {
				if (connection != null) {
					connection.close();
				} else {
					return false;
				}
			} catch (SQLException ee) {
				// connection close failed
				System.err.println(ee);
				return false;
			}
		}
		return true;
	}

	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// connection close failed
			System.err.println(e);
		}
	}

	/*
	 * return character Date of Birth (DOB)
	 * 
	 * @author melanyw
	 */
	public static String getDOB() {
		String DOB;

		int year = (int) ((Math.random() * 100) % 80) + 1917;
		int month = (int) ((Math.random() * 12) % 12) + 1;

		int day;
		// list of months with 31 days
		int[] longMonth = new int[] { 1, 3, 5, 7, 8, 10, 12 };
		// list of months with 30 days
		int[] shortMonth = new int[] { 4, 6, 9, 11 };

		if (Arrays.asList(longMonth).contains(month)) {
			day = (int) (Math.random() * ((31 - 1) + 1));

		} else if (Arrays.asList(shortMonth).contains(month)) {
			day = (int) (Math.random() * ((30 - 1) + 1));

			// February
		} else {
			day = (int) (Math.random() * ((28 - 1) + 1));
		}

		String monthWD = "";
		if (month == 1)
			monthWD = "Jan";
		if (month == 2)
			monthWD = "Feb";
		if (month == 3)
			monthWD = "Mar";
		if (month == 4)
			monthWD = "Apr";
		if (month == 5)
			monthWD = "May";
		if (month == 6)
			monthWD = "Jun";
		if (month == 7)
			monthWD = "Jul";
		if (month == 8)
			monthWD = "Aug";
		if (month == 9)
			monthWD = "Sep";
		if (month == 10)
			monthWD = "Oct";
		if (month == 11)
			monthWD = "Nov";
		if (month == 12)
			monthWD = "Dec";

		DOB = year + "-" + monthWD + "-" + day;

		return DOB;
	}

	public static int getDriversID() {
		return ((int) (Math.random() * ((9999999 - 1000000) + 1)));
	}

	// get random row number from table
	public static int randomID(String tableName) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("SELECT ID FROM "
					+ tableName + " ORDER BY Random() LIMIT 1");
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		int tableID = 0;
		try {
			tableID = rs.getInt(ID);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return tableID;
	}

	public static String getFName(int id) {
		String fName = "";

		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT firstName FROM firstName WHERE ID = "
							+ id);
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		try {
			fName = rs.getString(FIRSTNAME);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return fName;
	}

	public static String getLName(int id) {
		String lName = "";

		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT lastName FROM lastName WHERE ID = "
							+ id);
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		try {
			lName = rs.getString(LASTNAME);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return lName;
	}

	public static String getPassportID(int id) {
		String passport = "";

		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT passportID FROM passportID WHERE ID = "
							+ id);
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		try {
			passport = rs.getString(PASSPORTID);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return passport;
	}

	public static String getAddress(int id) {
		String address = "";

		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT address, city, postal, country FROM address WHERE ID = "
							+ id);
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		try {
			address = rs.getString(ADDRESS);
			address = address + " " + rs.getString(CITY);
			address = address + " " + rs.getString(POSTAL);
			address = address + " " + rs.getString(COUNTRY);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return address;
	}

	public static String getOccupation(int id) {
		String occupation = "";

		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT occupation FROM occupation WHERE ID = "
							+ id);
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		try {
			occupation = rs.getString(OCCUPATION);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return occupation;
	}

	public static void main(String[] args) {
		boolean success = openConnection("jdbc:sqlite:src/main/db/game.db");
		if (success) {
			System.out.println("Database connection successful");

			// quick demon on character attributes
			for (int i = 0; i < 10; i++) {
				String dob = getDOB();
				int DriversID = getDriversID();

				// Start getting Random stuff from database
				// First Name
				int r = randomID("firstName");
				String fName = getFName(r);

				// Last Name
				r = randomID("lastName");
				String lName = getLName(r);

				// Passport Number
				r = randomID("passportID");
				String passport = getPassportID(r);

				// Address
				r = randomID("address");
				String address = getAddress(r);

				// Occupation
				r = randomID("occupation");
				String occupation = getOccupation(r);

				System.out.println("");
				System.out.println("*********************************");
				System.out.println("Name: " + fName + " " + lName);
				System.out.println("Age: " + dob);
				System.out.println("Address: " + address);
				System.out.println("Drivers License: " + DriversID);
				System.out.println("Passport Number: " + passport);
				System.out.println("Occupation: " + occupation);

				System.out.println("*********************************");
				System.out.println("");
			}

		} else {
			System.out.println("Database connection failed");
		}
	}
}