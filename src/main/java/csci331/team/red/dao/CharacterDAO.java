package csci331.team.red.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * CSCI331-TAG MW SUPERCLASS
 * @author melany
 *
 */
public class CharacterDAO {
	public static DBConnection conn = DBConnection.getDBCon();
	public static final String ID = "ID";
	public static final String FIRSTNAME = "firstName";
	public static final String LASTNAME = "lastName";
	public static final String DRIVERSID = "driversID";
	public static final String DOB = "dob";
	public static final String PASSPORTID = "passportID";
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String REGION = "region";
	public static final String POSTAL = "postal";
	public static final String COUNTRY = "country";
	public static final String OCCUPATION = "occupation";

	public CharacterDAO() {
	}

	/**
	 * CSCI331-TAG MW OVERRIDING<br>
	 * <br>
	 * 
	 * Returns a NEW random character NOT in the current game play character
	 * database
	 * 
	 * @return Character
	 * @author melany
	 */
	public Character getCharacter() {
		return new Character(getDOB(), getDriversID(),
				getFName(randomID(FIRSTNAME)), getLName(randomID(LASTNAME)),
				getPassportID(randomID(PASSPORTID)),
				getAddress(randomID(ADDRESS)), getCity(randomID(CITY)),
				getRegion(randomID(REGION)), getPostal(randomID(POSTAL)),
				getCountry(randomID(COUNTRY)),
				getOccupation(randomID(OCCUPATION)));
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

		String monthString;
		switch (month) {
		case 1:
			monthString = "Jan";
			break;
		case 2:
			monthString = "Feb";
			break;
		case 3:
			monthString = "Mar";
			break;
		case 4:
			monthString = "Apr";
			break;
		case 5:
			monthString = "May";
			break;
		case 6:
			monthString = "Jun";
			break;
		case 7:
			monthString = "Jul";
			break;
		case 8:
			monthString = "Aug";
			break;
		case 9:
			monthString = "Sep";
			break;
		case 10:
			monthString = "Oct";
			break;
		case 11:
			monthString = "Nov";
			break;
		case 12:
			monthString = "Dec";
			break;
		default:
			monthString = "Invalid month";
			break;
		}

		DOB = year + "-" + monthString + "-" + day;

		return DOB;
	}

	public static String getDriversID() {
		return String
				.valueOf(((int) (Math.random() * ((9999999 - 1000000) + 1))));
	}

	// get random row number from table
	public static int randomID(String tableName) {
		PreparedStatement statement = null;
		try {
			statement = conn.connection.prepareStatement("SELECT ID FROM "
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
			statement = conn.connection
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
			statement = conn.connection
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
			statement = conn.connection
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
			statement = conn.connection
					.prepareStatement("SELECT address FROM address WHERE ID = "
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
		} catch (SQLException e) {
			System.err.println(e);
		}

		return address;
	}

	public static String getCity(int id) {
		String city = "";

		PreparedStatement statement = null;
		try {
			statement = conn.connection
					.prepareStatement("SELECT city FROM city WHERE ID = " + id);
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
			city = rs.getString(CITY);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return city;
	}

	public static String getRegion(int id) {
		String region = "";

		PreparedStatement statement = null;
		try {
			statement = conn.connection
					.prepareStatement("SELECT region FROM region WHERE ID = "
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
			region = rs.getString(REGION);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return region;
	}

	public static String getPostal(int id) {
		String postal = "";

		PreparedStatement statement = null;
		try {
			statement = conn.connection
					.prepareStatement("SELECT postal FROM postal WHERE ID = "
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
			postal = rs.getString(POSTAL);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return postal;
	}

	public static String getCountry(int id) {
		String country = "";

		PreparedStatement statement = null;
		try {
			statement = conn.connection
					.prepareStatement("SELECT country FROM country WHERE ID = "
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
			country = rs.getString(COUNTRY);
		} catch (SQLException e) {
			System.err.println(e);
		}

		return country;
	}

	public static String getOccupation(int id) {
		String occupation = "";

		PreparedStatement statement = null;
		try {
			statement = conn.connection
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

}
