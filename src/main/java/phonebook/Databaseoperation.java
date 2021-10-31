package phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Database operations helper class
public class Databaseoperation {
	
	public static Connection con;
	public static void openConnection()
	{
		con = DBconnection.connect();
	}
	public static void closeConnection()
	{
		DBconnection.close();;
	}
	
	//Database insert and get and delete functions
	public static boolean insertUsersQuery(String username,String password)
	{
		PreparedStatement stmt;
	    String sqlInsert = "insert into users (username,password) values(?,?)";
	    try {
			stmt = con.prepareStatement(sqlInsert);
			stmt.setString(1,username);
			stmt.setString(2,password);
			int i=stmt.executeUpdate();  
			System.out.println(i+" records inserted");
			return true;
		} catch (SQLException e1) {
			Restservice.log.error(e1.getMessage());
			return false;

		}
	    
	}
	
	public static User getUserQuery(String username)
	{
		User user = null;
		PreparedStatement stmt;
	    ResultSet rst = null;
		String sql="select * from users where userName = ?;"; 
	    try {
			stmt=con.prepareStatement(sql);
			stmt.setString(1,username);
			rst = stmt.executeQuery();
			while(rst.next()) {
				user = new User(rst.getInt("user_id"),rst.getString("username"),rst.getString("password"));
	         }
			return user;
		} catch (SQLException e) {
			Restservice.log.error(e.getMessage());
			return user;
		}  
	    
	}
	
	public static boolean insertContactQuery(int userId,String contactName, String contactNumber, String contactEmail, String contactStatus,String contact_picture)
	{
		PreparedStatement stmt;
	    String sqlInsert = "insert into contacts (user_id,contact_name,	contact_number,contact_email,contact_status,contact_picture) values(?,?,?,?,?,?);";
	    try {
			stmt = con.prepareStatement(sqlInsert);
			stmt.setInt(1,userId);
			stmt.setString(2,contactName);
			stmt.setString(3,contactNumber);
			stmt.setString(4,contactEmail);
			stmt.setString(5,contactStatus);
			stmt.setString(6, contact_picture);
			int i=stmt.executeUpdate();  
			System.out.println(i+" records inserted");
			return true;
		} catch (SQLException e1) {
			Restservice.log.error(e1.getMessage());
			return false;

		}
	    
	}
	
	public static boolean deleteContactQuery(int contactId)
	{
		PreparedStatement stmt;
	    String sqlInsert = "delete from contacts where contact_id = ?;";
	    try {
			stmt = con.prepareStatement(sqlInsert);
			stmt.setInt(1,contactId);
			int i=stmt.executeUpdate();  
			System.out.println(i+" records deleted");
			return true;
		} catch (SQLException e1) {
			Restservice.log.error(e1.getMessage());
			return false;

		}
	    
	}
	
	public static List<Contacts> getContactQuery(int userId,int limit, int offset)
	{
		List <Contacts> contacts = new ArrayList<>();
		PreparedStatement stmt;
	    ResultSet rst = null;
		String sql="select * from contacts where user_id = ? limit ? offset ?;"; 
	    try {
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, limit);
			stmt.setInt(3, offset);
			rst = stmt.executeQuery();
			while(rst.next()) {
				contacts.add(new Contacts(rst.getInt("contact_id"),userId,rst.getString("contact_name"),rst.getString("contact_number"),rst.getString("contact_email"),rst.getString("contact_status"),rst.getString("contact_picture")));
			}
			return contacts;
		} catch (SQLException e) {
			Restservice.log.error(e.getMessage());
			return contacts;
		}
	}
	
	public static int getContactCount(int userId)
	{
		int count = 0;
		PreparedStatement stmt;
	    ResultSet rst = null;
		String sql="select count(*) from contacts where user_id = ?;"; 
	    try {
			stmt=con.prepareStatement(sql);
			stmt.setInt(1, userId);
			rst = stmt.executeQuery();
			while(rst.next()) {
				count = rst.getInt("count(*)");
			}
			return count;
		} catch (SQLException e) {
			Restservice.log.error(e.getMessage());
			return count;
		}
	}
	
    

}
