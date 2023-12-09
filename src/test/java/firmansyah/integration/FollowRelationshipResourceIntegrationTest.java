// created by the factor : Dec 9, 2023, 9:19:14 AM  
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

import org.apache.http.HttpStatus;
import firmansyah.application.web.model.request.NewFollowRelationshipRequest;
import firmansyah.application.web.model.request.UpdateFollowRelationshipRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FollowRelationshipResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String FOLLOWRELATIONSHIP_RESOURCE_PATH = API_PREFIX + "/firmansyah/followRelationship";
  
	@Test
  	public void givenANewFollowRelationship_whenExecuteCreateEndpoint_shouldReturnCreatedFollowRelationship201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewFollowRelationshipRequest followRelationship = new NewFollowRelationshipRequest();
			final var usersFollowedIdEntity= createUsers("");
			followRelationship.setFollowedId( usersFollowedIdEntity.getId());
			final var usersUserIdEntity= createUsers("");
			followRelationship.setUserId( usersUserIdEntity.getId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(followRelationship)).when()
		       .post(FOLLOWRELATIONSHIP_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"followRelationship.usersFollowedIdResponse.id",
					 Matchers.notNullValue(),
					"followRelationship.usersFollowedIdResponse.id",
					 is( usersFollowedIdEntity.getId()),
					"followRelationship.usersUserIdResponse.id",
					 Matchers.notNullValue(),
					"followRelationship.usersUserIdResponse.id",
					 is( usersUserIdEntity.getId())
					);
  	}
  
  	@Test
  	public void givenANewFollowRelationshipWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewFollowRelationshipRequest followRelationship = new NewFollowRelationshipRequest();
			final var usersFollowedIdEntity= createUsers("");
			followRelationship.setFollowedId( usersFollowedIdEntity.getId());
			final var usersUserIdEntity= createUsers("");
			followRelationship.setUserId( usersUserIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.post(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidFollowRelationship_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewFollowRelationshipRequest followRelationship = new NewFollowRelationshipRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.when()
        	.post(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"followedId of FollowRelationship must not be blank",
						"userId of FollowRelationship must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentFollowRelationship_whenExecuteCreateEndpoint_shouldReturnConflictFollowRelationshipRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var followRelationshipEntity = createFollowRelationship("");
		NewFollowRelationshipRequest followRelationship = new NewFollowRelationshipRequest();
			followRelationship.setFollowedId(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId());
			followRelationship.setUserId(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.post(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("followrelationship already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentFollowRelationship_whenExecuteUpdateEndpoint_shouldReturnUpdatedFollowRelationship()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var followRelationshipEntity = createFollowRelationship("");
		UpdateFollowRelationshipRequest followRelationship = new UpdateFollowRelationshipRequest();
			followRelationship.setFollowedId(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId());
			followRelationship.setUserId(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.put(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"followRelationship.usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship.usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship.usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
        		);
        	
			Assertions.assertEquals(followRelationship.getFollowedId(),
			 followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId());Assertions.assertEquals(followRelationship.getUserId(),
			 followRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
  	}
  
  	@Test
  	public void givenAExistentFollowRelationshipWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var followRelationshipEntity = createFollowRelationship("");
		UpdateFollowRelationshipRequest followRelationship = new UpdateFollowRelationshipRequest();
			followRelationship.setFollowedId(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId());
			followRelationship.setUserId(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.put(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentFollowRelationship_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateFollowRelationshipRequest followRelationship = new UpdateFollowRelationshipRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.when()
        	.put(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"followedId of FollowRelationship must not be blank",
						"userId of FollowRelationship must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentFollowRelationship_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateFollowRelationshipRequest followRelationship = new UpdateFollowRelationshipRequest();
			final var usersFollowedIdEntity= createUsers("");
			followRelationship.setFollowedId( usersFollowedIdEntity.getId());
			final var usersUserIdEntity= createUsers("");
			followRelationship.setUserId( usersUserIdEntity.getId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.when()
        	.put(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("followRelationship not found"));
  	}
  
  	@Test
  	public void givenANewFollowRelationshipWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewFollowRelationshipRequest followRelationship = new NewFollowRelationshipRequest();
			followRelationship.setFollowedId("followedId");
			followRelationship.setUserId("userId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(followRelationship))
        	.when()
        	.post(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("users not found"),hasItems("users not found")));
  	}
  
   	@Test
   	public void givenAExistentFollowRelationshipWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var followRelationshipEntity = createFollowRelationship("");
		UpdateFollowRelationshipRequest followRelationship = new UpdateFollowRelationshipRequest();
			followRelationship.setFollowedId("followedId");
			followRelationship.setUserId("userId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(followRelationship))
         	.when()
         	.put(FOLLOWRELATIONSHIP_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("followRelationship not found"),hasItems("users not found"),hasItems("users not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentFollowRelationship_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("followedId", "followedId")
			.queryParam("userId", "userId")
 	        .delete(FOLLOWRELATIONSHIP_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentFollowRelationship_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var followRelationshipEntity = createFollowRelationship("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("followedId", followRelationshipEntity.getUsersFollowedId().getId())
		.queryParam("userId", followRelationshipEntity.getUsersUserId().getId())
 	        .delete(FOLLOWRELATIONSHIP_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findFollowRelationshipEntityByPK(
		 followRelationshipEntity.getUsersFollowedId().getId()
		,		     
		 followRelationshipEntity.getUsersUserId().getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentFollowRelationshipWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var followRelationshipEntity = createFollowRelationship("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		.queryParam("followedId", followRelationshipEntity.getUsersFollowedId().getId())
		.queryParam("userId", followRelationshipEntity.getUsersUserId().getId())
        	.delete(FOLLOWRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentFollowRelationship_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var followRelationshipEntity = createFollowRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("followedId", followRelationshipEntity.getUsersFollowedId().getId())
		.queryParam("userId", followRelationshipEntity.getUsersUserId().getId())
			.get(FOLLOWRELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"followRelationship.usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship.usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship.usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentFollowRelationship_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var followRelationshipEntity = createFollowRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("followedId", "followedId")
			.queryParam("userId", "userId")
			.get(FOLLOWRELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentFollowRelationshipWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var followRelationshipEntity = createFollowRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		.queryParam("followedId", followRelationshipEntity.getUsersFollowedId().getId())
		.queryParam("userId", followRelationshipEntity.getUsersUserId().getId())
			.get(FOLLOWRELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"followRelationship.usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship.usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship.usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
	}
   
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset0AndLimit5_shouldReturnListOf5FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"followRelationship[0]",
					 hasKey("usersFollowedIdResponse"),
					"followRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"followRelationshipCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset0AndLimit10_shouldReturnListOf10FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"followRelationship[0]",
					 hasKey("usersFollowedIdResponse"),
					"followRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"followRelationshipCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset0AndLimit15_shouldReturnListOf10FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"followRelationship[0]",
					 hasKey("usersFollowedIdResponse"),
					"followRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"followRelationshipCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset1AndLimit5_shouldReturnListOf5FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"followRelationship[0]",
					 hasKey("usersFollowedIdResponse"),
					"followRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"followRelationshipCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset2AndLimit5_shouldReturnListOf0FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"followRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset1AndLimit10_shouldReturnListOf0FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"followRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilterWithOffset1AndLimit115_shouldReturnListOf0FollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"followRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|neq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|neq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 not(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 not(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|like|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|like|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|nlike|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|nlike|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 not(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 not(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","followedId,userId"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredFollowRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-followedId,-userId"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"followRelationship[0].usersFollowedIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersFollowedIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersFollowedId().getId()),
				"followRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"followRelationship[0].usersUserIdResponse.id",
				 is(followRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedIdXXX|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userIdXXX|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","followedId,userId"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eqXXX|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eqXXX|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","followedId,userId"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","followedId,userId"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							""+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","followedIdXXX,userIdXXX"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10FollowRelationship_whenExecuteFindFollowRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var followRelationshipEntity = createFollowRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFollowRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "followedId|eq|"+followRelationshipEntity.getUsersFollowedId().getId()+
							";:')"+"||"+"userId|eq|"+followRelationshipEntity.getUsersUserId().getId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","followedId,userId"
	        )
	        .get(FOLLOWRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
