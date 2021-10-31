package phonebook;

import java.sql.*;  

public class DBconnection {

	public static Connection con;
	private static String conn_string = "jdbc:mysql://127.0.0.1:3306/phonebook?characterEncoding=latin1";
	private static String username = "root";
	private static String password = "";
	//make the database connection
	public static Connection connect() {
		try
		{  
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(conn_string,username,password);  
			Restservice.log.info("Database connection Established at: "+conn_string);
			return con;
			
		}
		catch(Exception e)
		{ 
			Restservice.log.error(e.getMessage());
			return null;
		}  
	}
	
	//close the database connection
	public static void close() {
		try {
			con.close();
			Restservice.log.info("Database connection closed from : "+conn_string);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Restservice.log.error(e.getMessage());
		}
	}
	
	
}
