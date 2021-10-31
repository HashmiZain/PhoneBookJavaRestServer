package phonebook;

//Enum for standard Success/Error response
public enum  Responsestatus {
    SUCCESS ("Success"),
    ERROR ("Error");
	private String status;       

    Responsestatus(String response) {
    	this.status = response;
    }
    
    public String getStatus()
    {
    	return status;
    }

}
