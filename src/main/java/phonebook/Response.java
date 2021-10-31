package phonebook;

import com.google.gson.JsonElement;

//Class of sending json response
public class Response {
    private Responsestatus status;
    private String message;
    private JsonElement data;
    
    
    //Constructors
    public Response(Responsestatus status) {
        this.status = status;
    }
    public Response(Responsestatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public Response(Responsestatus status, JsonElement data) {
        this.status = status;
        this.data = data;
    }
    
    
    //Setter and getter methods
    public void setStatus(Responsestatus status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
       
    public void setData(JsonElement data) {
        this.data = data;
    }
    
    public Responsestatus getStatus() {
    	return this.status;
    }
    public String getMessage() {
    	return this.message;
    }
    public JsonElement getData() {
    	return this.data;
    }
}


