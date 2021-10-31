package phonebook;


//Class the hold user details
public class User {
	private int id;
    private String username;
    private String password;
    
    User(int id,String username,String password)
    {
    	this.id = id;
    	this.username = username;
    	this.password = password;
    }
    
    //Getter methods
    public String getUserName()
    {
    	return this.username;
    }
    public String getPassword()
    {
    	return this.password;
    }
    public int getId()
    {
    	return this.id;
    }

}
