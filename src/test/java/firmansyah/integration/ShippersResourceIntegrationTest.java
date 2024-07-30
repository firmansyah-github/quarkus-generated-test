// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.integration;

import static io.restassured.RestAssured.given;
import static firmansyah.utils.TestConstants.API_PREFIX;
import static firmansyah.utils.TestConstants.AUTHORIZATION_HEADER;
import static firmansyah.utils.TestConstants.AUTHORIZATION_HEADER_VALUE_PREFIX;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.anyOf;

import jakarta.ws.rs.core.MediaType;

import java.util.Base64;

import org.apache.http.HttpStatus;
import firmansyah.application.web.model.request.NewShippersRequest;
import firmansyah.application.web.model.request.UpdateShippersRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;

@QuarkusTest
public class ShippersResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String SHIPPERS_RESOURCE_PATH = API_PREFIX + "/firmansyah/shippers";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewShippers_whenExecuteCreateEndpoint_shouldReturnCreatedShippers201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewShippersRequest shippers = new NewShippersRequest();
			shippers.setShipperId(2);
			shippers.setCompanyName("companyName");
			shippers.setPhone("phone");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(shippers)).when()
		       .post(SHIPPERS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"shippers.shipperId",
					 Matchers.notNullValue(),
					"shippers.shipperId",
					 is(shippers.getShipperId()),
					"shippers.companyName",
					 Matchers.notNullValue(),
					"shippers.companyName",
					 is(shippers.getCompanyName()),
					"shippers.phone",
					 Matchers.notNullValue(),
					"shippers.phone",
					 is(shippers.getPhone())
					);
  	}
  
  	@Test
  	public void givenANewShippersWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewShippersRequest shippers = new NewShippersRequest();
			shippers.setShipperId(2);
			shippers.setCompanyName("companyName");
			shippers.setPhone("phone");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(shippers))
        	.post(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidShippers_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewShippersRequest shippers = new NewShippersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(shippers))
        	.when()
        	.post(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"shipperId of Shippers must not be blank",
						"companyName of Shippers must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentShippers_whenExecuteCreateEndpoint_shouldReturnConflictShippersRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var shippersEntity = createShippers("");
		NewShippersRequest shippers = new NewShippersRequest();
			shippers.setShipperId(shippersEntity.getShipperId());
			shippers.setCompanyName("companyName");
			shippers.setPhone("phone");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(shippers))
        	.post(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("shippers already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentShippers_whenExecuteUpdateEndpoint_shouldReturnUpdatedShippers()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var shippersEntity = createShippers("");
		UpdateShippersRequest shippers = new UpdateShippersRequest();
			shippers.setShipperId(shippersEntity.getShipperId());
			shippers.setCompanyName("companyName");
			shippers.setPhone("phone");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(shippers))
        	.put(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"shippers.shipperId",
				 Matchers.notNullValue(),
				"shippers.shipperId",
				 is(shippers.getShipperId()),
				"shippers.companyName",
				 Matchers.notNullValue(),
				"shippers.companyName",
				 is(shippers.getCompanyName()),
				"shippers.phone",
				 Matchers.notNullValue(),
				"shippers.phone",
				 is(shippers.getPhone())
        		);
        	
			Assertions.assertEquals(shippers.getShipperId(),
			 shippersEntity.getShipperId());Assertions.assertEquals(shippers.getCompanyName(),
			 shippersEntity.getCompanyName());Assertions.assertEquals(shippers.getPhone(),
			 shippersEntity.getPhone());
  	}
  
  	@Test
  	public void givenAExistentShippersWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var shippersEntity = createShippers("");
		UpdateShippersRequest shippers = new UpdateShippersRequest();
			shippers.setShipperId(shippersEntity.getShipperId());
			shippers.setCompanyName("companyName");
			shippers.setPhone("phone");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(shippers))
        	.put(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentShippers_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateShippersRequest shippers = new UpdateShippersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(shippers))
        	.when()
        	.put(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"shipperId of Shippers must not be blank",
						"companyName of Shippers must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentShippers_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateShippersRequest shippers = new UpdateShippersRequest();
			shippers.setShipperId(2);
			shippers.setCompanyName("companyName");
			shippers.setPhone("phone");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(shippers))
        	.when()
        	.put(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("shippers not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentShippers_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("shipperId", 2)
 	        .delete(SHIPPERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentShippers_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var shippersEntity = createShippers("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("shipperId", shippersEntity.getShipperId())
 	        .delete(SHIPPERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findShippersEntityByPK(
	 shippersEntity.getShipperId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentShippersWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var shippersEntity = createShippers("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("shipperId", shippersEntity.getShipperId())
        	.delete(SHIPPERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentShippers_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var shippersEntity = createShippers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("shipperId", shippersEntity.getShipperId())
			.get(SHIPPERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"shippers.shipperId",
				 Matchers.notNullValue(),
				"shippers.shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers.companyName",
				 Matchers.notNullValue(),
				"shippers.companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers.phone",
				 Matchers.notNullValue(),
				"shippers.phone",
				 is(shippersEntity.getPhone())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentShippers_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var shippersEntity = createShippers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("shipperId", 2)
			.get(SHIPPERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentShippersWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var shippersEntity = createShippers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("shipperId", shippersEntity.getShipperId())
			.get(SHIPPERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"shippers.shipperId",
				 Matchers.notNullValue(),
				"shippers.shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers.companyName",
				 Matchers.notNullValue(),
				"shippers.companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers.phone",
				 Matchers.notNullValue(),
				"shippers.phone",
				 is(shippersEntity.getPhone())
				);
	}
   
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilterWithOffset0AndLimit5_shouldReturnListOf5Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"shippers[0]",
					 hasKey("shipperId"),
					"shippers[0]",
					 hasKey("companyName"),
					"shippers[0]",
					 hasKey("phone") ,
					"shippersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilterWithOffset0AndLimit10_shouldReturnListOf10Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"shippers[0]",
					 hasKey("shipperId"),
					"shippers[0]",
					 hasKey("companyName"),
					"shippers[0]",
					 hasKey("phone") ,
					"shippersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilterWithOffset0AndLimit15_shouldReturnListOf10Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"shippers[0]",
					 hasKey("shipperId"),
					"shippers[0]",
					 hasKey("companyName"),
					"shippers[0]",
					 hasKey("phone") ,
					"shippersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Shippers_whenExecuteFindShippersByFilterWithOffset10AndLimit5_shouldReturnListOf5Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"shippers[0]",
					 hasKey("shipperId"),
					"shippers[0]",
					 hasKey("companyName"),
					"shippers[0]",
					 hasKey("phone") ,
					"shippersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilterWithOffset20AndLimit5_shouldReturnListOf0Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"shippersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilterWithOffset10AndLimit10_shouldReturnListOf0Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"shippersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilterWithOffset10AndLimit115_shouldReturnListOf0Shippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createShippers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"shippersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 is(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 is(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|neq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|neq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|neq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 not(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 not(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 not(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|like|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|like|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|like|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 is(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|nlike|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|nlike|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|nlike|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 not(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 not(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 not(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","shipperId,companyName,phone"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 is(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredShippers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","-shipperId,-companyName,-phone"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"shippers[0].shipperId",
				 Matchers.notNullValue(),
				"shippers[0].shipperId",
				 is(shippersEntity.getShipperId()),
				"shippers[0].companyName",
				 Matchers.notNullValue(),
				"shippers[0].companyName",
				 is(shippersEntity.getCompanyName()),
				"shippers[0].phone",
				 Matchers.notNullValue(),
				"shippers[0].phone",
				 is(shippersEntity.getPhone())
				);
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperIdXXX|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyNameXXX|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phoneXXX|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","shipperId,companyName,phone"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eqXXX|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eqXXX|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eqXXX|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","shipperId,companyName,phone"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","shipperId,companyName,phone"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							""+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							""+"||"+"phone|eq|"+shippersEntity.getPhone()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","shipperIdXXX,companyNameXXX,phoneXXX"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Shippers_whenExecuteFindShippersByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var shippersEntity = createShippers("");
	    for (int i = 0; i < 9; i++) {
	    	createShippers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "shipperId|eq|"+shippersEntity.getShipperId()+
							";:')"+"||"+"companyName|eq|"+shippersEntity.getCompanyName()+
							";:')"+"||"+"phone|eq|"+shippersEntity.getPhone()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","shipperId,companyName,phone"
	        )
	        .get(SHIPPERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
