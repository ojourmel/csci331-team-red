package csci331.team.red.dao;

import java.sql.Connection;

/**
 * CSCI331-TAG MW SINGLETON PATTERN <br>
 * <br>
 * 
 * DBConnection creates a single database connection.
 * By restricting the number of instances of a DBConnection to one,
 * This is a singleton pattern. <br>
 * 
 * The constructor of DBConnection is private, and holds the code
 * that creates a database connection.<br>
 * 
 * Because the constructor is private another method, getDB(), controls
 * the number of DBConnections we want to establish, in this case one.
 * getDBCon() checks the unique instance variable, db, and if it is null
 * than the constructor is called and a new DBConnection is returned.
 * Otherwise, the existing DBConnection is returned.<br>
 * 
 * By doing this, we are able to limit the number of instances
 * of DBConnection to one.  <br>
 * 
 * The singleton pattern was employed because only one database connection
 * needs to be instantiated/game play.
 * 
 * 
 * @author melany
 * 
 */
public final class DBConnection {
	public Connection connection = null;
	public static DBConnection db; // This is the unique instance variable

	/**
	 * The constructor of the Singleton class is private, this prevents unwanted
	 * access to it, and allows control over the number of DBConnections created
	 */
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
	 * This is the method that will be called to create a database connection. <br>
	 * 
	 * getDBCon() will check if there is already a database connection
	 * established, if there is it returns that DBConnection, else it creates a
	 * new DBConnection.<br>
	 * 
	 * In java Synchronized solves multi-threading problems surrounding object
	 * creation, but can be expensive at runtime
	 * 
	 * @return DBConnection database connection object
	 * @author melany
	 */
	public static synchronized DBConnection getDBCon() {
		if (db == null) {
			db = new DBConnection();
		}
		return db;
	}

}