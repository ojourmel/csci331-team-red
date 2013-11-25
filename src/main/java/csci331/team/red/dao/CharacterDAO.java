package csci331.team.red.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import csci331.team.red.shared.Character;
import csci331.team.red.shared.Gender;
import csci331.team.red.shared.PersonPicture;

/**
 * CSCI331-TAG MW SUPERCLASS<br>
 * <br>
 * 
 * CharacterDAO is the superclass of CharacterRepository. CharacterDAO was
 * created as a superclass as it randomly creates characters from database data.
 * It also contains several global variables, that are needed by other classes.
 * 
 * @author melany
 * 
 */
public class CharacterDAO {
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
	public static final String GENDER = "gender";

	private static final int EARLIEST_YEAR = Calendar.getInstance().get(
			Calendar.YEAR) - 95;
	private static final int LATEST_YEAR = Calendar.getInstance().get(
			Calendar.YEAR) - 16;

	private static DBConnection conn = DBConnection.getDBCon();

	/**
	 * Returns a NEW random character NOT in the current game play character
	 * database, rather than the CharacterRepository.getCharacter(), which may
	 * returns a random character of already created characters in the game-play
	 * database
	 * 
	 * @return Character
	 * @author melany
	 */
	public Character getCharacter() {
		int fNameID = randomID(FIRSTNAME);

		Gender g = Gender.fromChar(getGender(fNameID));

		return new Character(getDOB(), getDriversID(), getFName(fNameID),
				getLName(randomID(LASTNAME)),
				getPassportID(randomID(PASSPORTID)),
				getAddress(randomID(ADDRESS)), getCity(randomID(CITY)),
				getRegion(randomID(REGION)), getPostal(randomID(POSTAL)),
				getCountry(randomID(COUNTRY)),
				getOccupation(randomID(OCCUPATION)), g,
				PersonPicture.getRandom(g));
	}

	/**
	 * Randomly generate a character's date of birth
	 * 
	 * @return Date of Birth
	 * @author melanyw
	 */
	public String getDOB() {
		String dob;

		GregorianCalendar gc = new GregorianCalendar();

		int year = randBetween(1900, 2010);

		gc.set(Calendar.YEAR, year);

		int dayOfYear = randBetween(1,
				gc.getActualMaximum(Calendar.DAY_OF_YEAR));

		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

		String month = gc.getDisplayName(Calendar.MONTH, Calendar.SHORT,
				Locale.CANADA);

		dob = gc.get(Calendar.YEAR) + "-" + month + "-"
				+ gc.get(Calendar.DAY_OF_MONTH);
		return dob;

	}

	private int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	/**
	 * Randomly generate a Drivers Licence number
	 * 
	 * @return DriversID
	 * @author melany
	 */
	public String getDriversID() {
		// TODO: Verify data using jUnit testing
		return String
				.valueOf(((int) (Math.random() * ((9999999 - 1000000) + 1) + 1000000)));
	}

	/**
	 * Fetches a first name from the database based on the id
	 * 
	 * @param id
	 * @return FirstName from database
	 * @author melany
	 */
	public String getFName(int id) {
		return executeStatement(FIRSTNAME, id);
	}

	/**
	 * Fetches a last name from the database based on the id
	 * 
	 * @param id
	 * @return LastName from database
	 * @author melany
	 */
	public String getLName(int id) {
		return executeStatement(LASTNAME, id);
	}

	/**
	 * Fetches a passport id from the database based on the id
	 * 
	 * @param id
	 * @return PassportID from database
	 * @author melany
	 */
	public String getPassportID(int id) {
		return executeStatement(PASSPORTID, id);
	}

	/**
	 * Fethces an address from the database based on the id
	 * 
	 * @param id
	 * @return address from database
	 * @author melany
	 */
	public String getAddress(int id) {
		return executeStatement(ADDRESS, id);
	}

	/**
	 * Fetches a city from the database based on the id
	 * 
	 * @param id
	 * @return city from the database
	 * @author melany
	 */
	public String getCity(int id) {
		return executeStatement(CITY, id);
	}

	/**
	 * Fetches a region(e.g. province or state) from the database based on the
	 * id
	 * 
	 * @param id
	 * @return region from the database
	 * @author melany
	 */
	public String getRegion(int id) {
		return executeStatement(REGION, id);
	}

	/**
	 * Fetches a postal/zip code from the database based on the id
	 * 
	 * @param id
	 * @return postal code from the database
	 * @author melany
	 */
	public String getPostal(int id) {
		return executeStatement(POSTAL, id);
	}

	/**
	 * Fetches a country from the database based on the id
	 * 
	 * @param id
	 * @return country from the database
	 * @author melany
	 */
	public String getCountry(int id) {
		return executeStatement(COUNTRY, id);
	}

	/**
	 * Fetches an occupation from the database based on the id
	 * 
	 * @param id
	 * @return occupation from the database
	 * @author melany
	 */
	public String getOccupation(int id) {
		return executeStatement(OCCUPATION, id);
	}

	private char getGender(int id) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		char c;

		try {
			if (conn.connection.isClosed()) {
				System.err.println("OMG");
			}
			statement = conn.connection.prepareStatement("SELECT " + GENDER
					+ " FROM " + FIRSTNAME + " WHERE ID = ?");
			statement.setInt(1, id);
			rs = statement.executeQuery();

			c = rs.getString(GENDER).charAt(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return c;
	}

	/**
	 * Fetches a random id based on the database entity
	 * 
	 * @param tableName
	 * @return random id
	 * @author melany
	 */
	public int randomID(String tableName) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		int tableID = -1;

		try {
			if (conn.connection.isClosed()) {
				System.err.println("OMG");
			}
			statement = conn.connection.prepareStatement("SELECT ID FROM "
					+ tableName + " ORDER BY Random() LIMIT 1");

			rs = statement.executeQuery();
			tableID = rs.getInt(ID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return tableID;
	}

	/**
	 * Executes, and returns the contents of a default select query: <br>
	 * 
	 * <i> Select ? FROM ? WHERE ID = ? </i>
	 * 
	 * @param column
	 * @param id
	 * @return the single result of the query, or null, if does not exist
	 * @author melany
	 */
	private String executeStatement(String column, int id) {
		String entry = "";
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.connection.prepareStatement("SELECT " + column
					+ " FROM  " + column + " WHERE ID = ?");
			statement.setInt(1, id);
			rs = statement.executeQuery();
			entry = rs.getString(column);
		} catch (SQLException e) {
			// If anything when wrong with the database, it's because the query
			// is bad, or the database is corrupt. Either way, it is a
			// un-recoverable error, and is the programmers fault. End the
			// program.
			throw new RuntimeException(e);
		}
		return entry;
	}

	protected boolean isValid(String column, String value) {
		boolean isValid = false;

		if (column == DOB) {
			DateFormat formatter = new SimpleDateFormat("YYYY-MMM-DD");
			formatter.setLenient(false);
			Date date = null;
			try {
				date = formatter.parse(value);
			} catch (ParseException e) {
				return false;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			Calendar eDate = Calendar.getInstance();
			eDate.set(Calendar.YEAR, EARLIEST_YEAR);

			Calendar lDate = Calendar.getInstance();
			lDate.set(Calendar.YEAR, LATEST_YEAR);

			return (calendar.after(eDate) && calendar.before(lDate));
		} else if (column == DRIVERSID) {
			return ((Integer.valueOf(value) > 1000000) && (Integer
					.valueOf(value) < 9999999));
		}

		PreparedStatement statement = null;
		ResultSet rs = null;

		try {

			statement = conn.connection.prepareStatement("SELECT " + column
					+ " FROM  " + column + " WHERE " + column + " =  ?");
			statement.setString(1, value);
			rs = statement.executeQuery();
			isValid = rs.next();
		} catch (SQLException e) {
			// If anything when wrong with the database, it's because the query
			// is bad, or the database is corrupt. Either way, it is a
			// un-recoverable error, and is the programmers fault. End the
			// program.
			throw new RuntimeException(e);
		}
		return isValid;
	}

}