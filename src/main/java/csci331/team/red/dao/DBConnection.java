package csci331.team.red.dao;

import java.sql.Connection;

/**
 * CSCI331-TAG MW SINGLETON PATTERN <br>
 * <br>
 * 
 * Creates a database connection.
 * 
 * TODO: put in additional notes for singleton pattern description lookup what
 * synchronized means in java Synchronized soles multithreading problems
 * surrounding object creation, but can be expensive at runtime
 * 
 * @author melany
 * 
 */
public final class DBConnection {
	public Connection connection = null;
	public static DBConnection db; // This is the unique instance variable

	private DBConnection() {
		String url = "jdbc:sqlite:src/main/db/game.db";
		String driver = "org.sqlite.JDBC";

		try {
			Class.forName(driver).newInstance();
			this.connection = java.sql.DriverManager.getConnection(url);
		} catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * @return DBConnection database connection object
	 */
	public static synchronized DBConnection getDBCon() {
		if (db == null) {
			db = new DBConnection();
		}
		return db;
	}

}