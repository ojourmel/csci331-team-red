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
	/*
	 * Open Database Connection
	 */
	public static boolean openConnection(String url){
		try {
			Class.forName("org.sqlite.JDBC");
			connection = java.sql.DriverManager.getConnection(url);
		}
		catch(ClassNotFoundException cnfe) {
			System.err.println(cnfe.getMessage());
			return false;
		}
		catch(SQLException e) {
			System.err.println(e.getMessage());
			try {
				if(connection != null) {
					connection.close();
				} else {
					return false;
				}
			}
			catch(SQLException ee) {
				//connection close failed
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
		}
		catch (SQLException e) {
			//connection close failed
			System.err.println(e);
		}
	}
	
  /*
   * return character Date of Birth (DOB)
   * @author melanyw
   */
	public static String getDOB(){
		String DOB;
		
		int year = (int)((Math.random()*100) % 80) + 1917;
		int month = (int)((Math.random()*12) % 12) + 1;
		
		int day;
		//list of months with 31 days
		int[] longMonth = new int[]{1,3,5,7,8,10,12};
		//list of months with 30 days
		int[] shortMonth = new int[]{4,6,9,11};
		
		if(Arrays.asList(longMonth).contains(month)){
			day = (int)(Math.random()*((31-1)+1));
			
		} else if (Arrays.asList(shortMonth).contains(month)){
			day = (int)(Math.random()*((30-1)+1));
			
		//February
		} else {
			day = (int)(Math.random()*((28-1)+1));
		}
		
		String monthWD = "";
		if(month==1) monthWD = "Jan";
		if(month==2) monthWD = "Feb";
		if(month==3) monthWD = "Mar";
		if(month==4) monthWD = "Apr";
		if(month==5) monthWD = "May";
		if(month==6) monthWD = "Jun";
		if(month==7) monthWD = "Jul";
		if(month==8) monthWD = "Aug";
		if(month==9) monthWD = "Sep";
		if(month==10) monthWD = "Oct";
		if(month==11) monthWD = "Nov";
		if(month==12) monthWD = "Dec";
		
		DOB = year + "-" + monthWD + "-" + day;
		
		return DOB;
	}
	
	public static int getDriversID(){
		return((int)(Math.random()*((9999999-1000000)+1)));
	}
	
	//get random row number from table
	public static int randomID(String tableName){
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("SELECT ID FROM firstName ORDER BY Random() LIMIT 1");
		} catch (SQLException e) {
			System.err.println(e);
		}
		ResultSet rs = null;
		try {
			rs = statement.executeQuery();
		} catch (SQLException e) {
			System.err.println(e);
		}
		int maxID = 0;
		try {
			maxID = rs.getInt(ID);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return maxID;
		//return((int)(Math.random() * maxID + 1));
	}
	
  public static void main(String[] args)
  {
	  boolean success = openConnection("jdbc:sqlite:src/main/db/game.db");
	  if(success){
		  System.out.println("Database connection successful");	
		  
		  int s = randomID("firstName");
		  System.out.println(s);
		  
	  } else {
		  System.out.println("Database connection failed");
	  }
  }
}