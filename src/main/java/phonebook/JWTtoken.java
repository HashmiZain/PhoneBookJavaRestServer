package phonebook;

import java.util.Base64;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//JWT token creation and authentication class
public class JWTtoken {
	private String token;
	private final String secretKey = "Zainskey";//Secret key
	
	public String getToken()
	{
		return token;
	}
	
	//Token creation
	public void createJWT(String id, String issuer, String audience, long ttlMillis) {

	    //The JWT signature algorithm we will be using to sign the token
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);

	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);

	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	        .setIssuedAt(now)
	        .setIssuer(issuer)
	        .setAudience(audience)
	        .signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);
	    

	    //if it has been specified, let's add the expiration
	    if (ttlMillis >= 0) {
	      long expMillis = nowMillis + ttlMillis;
	      Date exp = new Date(expMillis);
	      builder.setExpiration(exp);
	    }

	    //Builds the JWT and serializes it to a compact, URL-safe string
	    this.token = builder.compact();
	  }
	
	
	//Token verification
	public int verifyToken(String jwtToken)
	{
		try {
			Claims claims = Jwts.parser() // Throws an exception if token cannot be verified        
		       .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
		       .parseClaimsJws(jwtToken).getBody();
			return Integer.parseInt(claims.getAudience());
		}
		 catch (Exception e) {
				Restservice.log.info(e.getMessage());
				return 0;
		}  
       
	}
	
	 
}
