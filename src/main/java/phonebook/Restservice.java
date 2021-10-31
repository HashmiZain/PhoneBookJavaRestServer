package phonebook;

import static spark.Spark.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

//Rest api/ main class
public class Restservice {
	//Logger initalization
	public static Logger log = LoggerFactory.getLogger(Restservice.class);
	
	public static void main(String[] args) {
		Databaseoperation.openConnection();//Open the database connection
		
		//Log incoming request details.
		before((req, res) -> {
			log.info("Request from " + req.ip()+" request context "+req.uri()+" request body "+req.body() + " received - " + req.userAgent());
		});
		
		//Api endpoint handlers
        post("/register", (request, response) -> {
            response.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            boolean status = Databaseoperation.insertUsersQuery(user.getUserName(),user.getPassword());
            if (status)
            	return new Gson().toJson(new Response(Responsestatus.SUCCESS));
            else
            	return new Gson().toJson(new Response(Responsestatus.ERROR));
        });
        get("/deletecontact", (request, response) -> {
            response.type("application/json");
            if (verifyToken(request.headers("token"))>0)
            {
            	boolean status = Databaseoperation.deleteContactQuery(Integer.parseInt(request.headers("contactid")));
            	if (status)
            			return new Gson().toJson(new Response(Responsestatus.SUCCESS));
            	else
                	return new Gson().toJson(new Response(Responsestatus.ERROR));
            }
            else
            	return new Gson().toJson(new Response(Responsestatus.ERROR));
            
        });
        post("/addcontact", (request, response) -> {
            response.type("application/json");
            if(verifyToken(request.headers("token"))>0)
            {
	            Contacts contact = new Gson().fromJson(request.body().toString(), Contacts.class);
	            String pic = null;
	            if(contact.getContactPicture() != null)
	            	pic = contact.getContactPicture();
	            boolean status = Databaseoperation.insertContactQuery(contact.getuserId(),contact.getcontactName(),contact.getcontactNumber(),contact.getcontactEmail(),contact.getcontactStatus(),pic);
	            if (status)
	            	return new Gson().toJson(new Response(Responsestatus.SUCCESS));
	            else
	            	return new Gson().toJson(new Response(Responsestatus.ERROR));
            }
            else
            	return new Gson().toJson(new Response(Responsestatus.ERROR));	
        });
        get("/getcontact", (request, response) -> {
            response.type("application/json");
            if(verifyToken(request.headers("token"))>0)
            {
	            List<Contacts> contact = Databaseoperation.getContactQuery(Integer.parseInt(request.headers("userid")),Integer.parseInt(request.headers("limit")),Integer.parseInt(request.headers("offset")));
				if(contact.size()>0)
					return new Gson().toJson(
	            	      new Response(Responsestatus.SUCCESS,new Gson().toJsonTree(contact)));
				else
					return new Gson().toJson(new Response(Responsestatus.ERROR));	
            }
            else
            	return new Gson().toJson(new Response(Responsestatus.ERROR));	
        });
        
        get("/auth", (request, response) -> {
            response.type("application/json");
            User user = Databaseoperation.getUserQuery(request.headers("username"));
        	JWTtoken token = new JWTtoken();
            if (user.getPassword().equals(request.headers("password")))
            {
                token.createJWT("0", "Zain",user.getId()+"", 10000000);
                log.info("User: "+request.headers("username")+" Authenticated.");
                return new Gson().toJson(
            	      new Response(Responsestatus.SUCCESS,new Gson().toJsonTree(token)));
            
            }
            else
                log.info("User: "+request.headers("username")+" Authentication Failed.");
            	return new Gson().toJson(
              	      new Response(Responsestatus.ERROR));    		 
        });
        get("/contactcount", (request, response) -> {
            response.type("application/json");
            if(verifyToken(request.headers("token"))>0)
            {
            	int count = Databaseoperation.getContactCount(Integer.parseInt(request.headers("userid")));
                return new Gson().toJson(
            	      new Response(Responsestatus.SUCCESS,count+""));
            
            }
            else
                log.info("User: "+request.headers("username")+" Authentication Failed.");
            	return new Gson().toJson(
              	      new Response(Responsestatus.ERROR));    		 
        });
        
       
        
    }
	public static int verifyToken(String tok) {
		JWTtoken token = new JWTtoken();
		return token.verifyToken(tok);
	}
	
	protected void finalize()  
	{  
		Databaseoperation.closeConnection();
	}  
	
}

