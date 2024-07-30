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

import org.apache.http.HttpStatus;
import firmansyah.application.web.model.request.NewUsersRequest;
import firmansyah.application.web.model.request.UpdateUsersRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UsersResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String USERS_RESOURCE_PATH = API_PREFIX + "/firmansyah/users";
  
	@Test
  	public void givenANewUsers_whenExecuteCreateEndpoint_shouldReturnCreatedUsers201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewUsersRequest users = new NewUsersRequest();
			users.setBio("bio");
			users.setEmail("email");
			users.setId("id");
			users.setImage("image");
			users.setPassword("password");
			users.setUsername("username");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(users)).when()
		       .post(USERS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"users.bio",
					 Matchers.notNullValue(),
					"users.bio",
					 is(users.getBio()),
					"users.email",
					 Matchers.notNullValue(),
					"users.email",
					 is(users.getEmail()),
					"users.id",
					 Matchers.notNullValue(),
					"users.id",
					 is(users.getId()),
					"users.image",
					 Matchers.notNullValue(),
					"users.image",
					 is(users.getImage()),
					"users.password",
					 Matchers.notNullValue(),
					"users.password",
					 is(users.getPassword()),
					"users.username",
					 Matchers.notNullValue(),
					"users.username",
					 is(users.getUsername())
					);
  	}
  
  	@Test
  	public void givenANewUsersWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewUsersRequest users = new NewUsersRequest();
			users.setBio("bio");
			users.setEmail("email");
			users.setId("id");
			users.setImage("image");
			users.setPassword("password");
			users.setUsername("username");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(users))
        	.post(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidUsers_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewUsersRequest users = new NewUsersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(users))
        	.when()
        	.post(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1),
            	"errors.body",
            	hasItems(
						"id of Users must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentUsers_whenExecuteCreateEndpoint_shouldReturnConflictUsersRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var usersEntity = createUsers("");
		NewUsersRequest users = new NewUsersRequest();
			users.setBio("bio");
			users.setEmail("email");
			users.setId(usersEntity.getId());
			users.setImage("image");
			users.setPassword("password");
			users.setUsername("username");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(users))
        	.post(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("users already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentUsers_whenExecuteUpdateEndpoint_shouldReturnUpdatedUsers()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var usersEntity = createUsers("");
		UpdateUsersRequest users = new UpdateUsersRequest();
			users.setBio("bio");
			users.setEmail("email");
			users.setId(usersEntity.getId());
			users.setImage("image");
			users.setPassword("password");
			users.setUsername("username");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(users))
        	.put(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"users.bio",
				 Matchers.notNullValue(),
				"users.bio",
				 is(users.getBio()),
				"users.email",
				 Matchers.notNullValue(),
				"users.email",
				 is(users.getEmail()),
				"users.id",
				 Matchers.notNullValue(),
				"users.id",
				 is(users.getId()),
				"users.image",
				 Matchers.notNullValue(),
				"users.image",
				 is(users.getImage()),
				"users.password",
				 Matchers.notNullValue(),
				"users.password",
				 is(users.getPassword()),
				"users.username",
				 Matchers.notNullValue(),
				"users.username",
				 is(users.getUsername())
        		);
        	
			Assertions.assertEquals(users.getBio(),
			 usersEntity.getBio());Assertions.assertEquals(users.getEmail(),
			 usersEntity.getEmail());Assertions.assertEquals(users.getId(),
			 usersEntity.getId());Assertions.assertEquals(users.getImage(),
			 usersEntity.getImage());Assertions.assertEquals(users.getPassword(),
			 usersEntity.getPassword());Assertions.assertEquals(users.getUsername(),
			 usersEntity.getUsername());
  	}
  
  	@Test
  	public void givenAExistentUsersWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var usersEntity = createUsers("");
		UpdateUsersRequest users = new UpdateUsersRequest();
			users.setBio("bio");
			users.setEmail("email");
			users.setId(usersEntity.getId());
			users.setImage("image");
			users.setPassword("password");
			users.setUsername("username");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(users))
        	.put(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentUsers_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateUsersRequest users = new UpdateUsersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(users))
        	.when()
        	.put(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"id of Users must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentUsers_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateUsersRequest users = new UpdateUsersRequest();
			users.setBio("bio");
			users.setEmail("email");
			users.setId("id");
			users.setImage("image");
			users.setPassword("password");
			users.setUsername("username");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(users))
        	.when()
        	.put(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("users not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentUsers_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
 	        .delete(USERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentUsers_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var usersEntity = createUsers("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", usersEntity.getId())
 	        .delete(USERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findUsersEntityByPK(
	 usersEntity.getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentUsersWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var usersEntity = createUsers("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", usersEntity.getId())
        	.delete(USERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentUsers_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var usersEntity = createUsers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", usersEntity.getId())
			.get(USERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"users.bio",
				 Matchers.notNullValue(),
				"users.bio",
				 is(usersEntity.getBio()),
				"users.email",
				 Matchers.notNullValue(),
				"users.email",
				 is(usersEntity.getEmail()),
				"users.id",
				 Matchers.notNullValue(),
				"users.id",
				 is(usersEntity.getId()),
				"users.image",
				 Matchers.notNullValue(),
				"users.image",
				 is(usersEntity.getImage()),
				"users.password",
				 Matchers.notNullValue(),
				"users.password",
				 is(usersEntity.getPassword()),
				"users.username",
				 Matchers.notNullValue(),
				"users.username",
				 is(usersEntity.getUsername())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentUsers_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var usersEntity = createUsers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
			.get(USERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentUsersWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var usersEntity = createUsers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", usersEntity.getId())
			.get(USERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"users.bio",
				 Matchers.notNullValue(),
				"users.bio",
				 is(usersEntity.getBio()),
				"users.email",
				 Matchers.notNullValue(),
				"users.email",
				 is(usersEntity.getEmail()),
				"users.id",
				 Matchers.notNullValue(),
				"users.id",
				 is(usersEntity.getId()),
				"users.image",
				 Matchers.notNullValue(),
				"users.image",
				 is(usersEntity.getImage()),
				"users.password",
				 Matchers.notNullValue(),
				"users.password",
				 is(usersEntity.getPassword()),
				"users.username",
				 Matchers.notNullValue(),
				"users.username",
				 is(usersEntity.getUsername())
				);
	}
   
	@Test
	public void given10Users_whenExecuteFindUsersByFilterWithOffset0AndLimit5_shouldReturnListOf5Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"users[0]",
					 hasKey("bio"),
					"users[0]",
					 hasKey("email"),
					"users[0]",
					 hasKey("id"),
					"users[0]",
					 hasKey("image"),
					"users[0]",
					 hasKey("password"),
					"users[0]",
					 hasKey("username") ,
					"usersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilterWithOffset0AndLimit10_shouldReturnListOf10Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"users[0]",
					 hasKey("bio"),
					"users[0]",
					 hasKey("email"),
					"users[0]",
					 hasKey("id"),
					"users[0]",
					 hasKey("image"),
					"users[0]",
					 hasKey("password"),
					"users[0]",
					 hasKey("username") ,
					"usersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilterWithOffset0AndLimit15_shouldReturnListOf10Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"users[0]",
					 hasKey("bio"),
					"users[0]",
					 hasKey("email"),
					"users[0]",
					 hasKey("id"),
					"users[0]",
					 hasKey("image"),
					"users[0]",
					 hasKey("password"),
					"users[0]",
					 hasKey("username") ,
					"usersCount",
					 is(10+1)
				);
				
	}
	
	@Test
	public void given15Users_whenExecuteFindUsersByFilterWithOffset10AndLimit5_shouldReturnListOf5Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"users[0]",
					 hasKey("bio"),
					"users[0]",
					 hasKey("email"),
					"users[0]",
					 hasKey("id"),
					"users[0]",
					 hasKey("image"),
					"users[0]",
					 hasKey("password"),
					"users[0]",
					 hasKey("username") ,
					"usersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilterWithOffset20AndLimit5_shouldReturnListOf0Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"usersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilterWithOffset1AndLimit10_shouldReturnListOf0Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"usersCount",
					 is(0+1)
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilterWithOffset10AndLimit115_shouldReturnListOf0Users() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"usersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							""+"||"+"email|eq|"+usersEntity.getEmail()+
							""+"||"+"id|eq|"+usersEntity.getId()+
							""+"||"+"image|eq|"+usersEntity.getImage()+
							""+"||"+"password|eq|"+usersEntity.getPassword()+
							""+"||"+"username|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 is(usersEntity.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 is(usersEntity.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 is(usersEntity.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 is(usersEntity.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 is(usersEntity.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 is(usersEntity.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							""+"||"+"email|eq|"+usersEntity.getEmail()+
							""+"||"+"id|eq|"+usersEntity.getId()+
							""+"||"+"image|eq|"+usersEntity.getImage()+
							""+"||"+"password|eq|"+usersEntity.getPassword()+
							""+"||"+"username|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 is(user.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 is(user.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 is(user.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 is(user.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 is(user.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 is(user.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio1", "image1", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|neq|"+usersEntity.getBio()+
							""+"||"+"email|neq|"+usersEntity.getEmail()+
							""+"||"+"id|neq|"+usersEntity.getId()+
							""+"||"+"image|neq|"+usersEntity.getImage()+
							""+"||"+"password|neq|"+usersEntity.getPassword()+
							""+"||"+"username|neq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 not(usersEntity.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 not(usersEntity.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 not(usersEntity.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 not(usersEntity.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 not(usersEntity.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 not(usersEntity.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|like|"+usersEntity.getBio()+
							""+"||"+"email|like|"+usersEntity.getEmail()+
							""+"||"+"id|like|"+usersEntity.getId()+
							""+"||"+"image|like|"+usersEntity.getImage()+
							""+"||"+"password|like|"+usersEntity.getPassword()+
							""+"||"+"username|like|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 is(usersEntity.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 is(usersEntity.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 is(usersEntity.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 is(usersEntity.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 is(usersEntity.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 is(usersEntity.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio1", "image1", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|nlike|"+usersEntity.getBio()+
							""+"||"+"email|nlike|"+usersEntity.getEmail()+
							""+"||"+"id|nlike|"+usersEntity.getId()+
							""+"||"+"image|nlike|"+usersEntity.getImage()+
							""+"||"+"password|nlike|"+usersEntity.getPassword()+
							""+"||"+"username|nlike|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 not(usersEntity.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 not(usersEntity.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 not(usersEntity.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 not(usersEntity.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 not(usersEntity.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 not(usersEntity.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							""+"||"+"email|eq|"+usersEntity.getEmail()+
							""+"||"+"id|eq|"+usersEntity.getId()+
							""+"||"+"image|eq|"+usersEntity.getImage()+
							""+"||"+"password|eq|"+usersEntity.getPassword()+
							""+"||"+"username|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","bio,email,id,image,password,username"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 is(usersEntity.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 is(usersEntity.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 is(usersEntity.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 is(usersEntity.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 is(usersEntity.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 is(usersEntity.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredUsers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							""+"||"+"email|eq|"+usersEntity.getEmail()+
							""+"||"+"id|eq|"+usersEntity.getId()+
							""+"||"+"image|eq|"+usersEntity.getImage()+
							""+"||"+"password|eq|"+usersEntity.getPassword()+
							""+"||"+"username|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-bio,-email,-id,-image,-password,-username"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"users[0].bio",
				 Matchers.notNullValue(),
				"users[0].bio",
				 is(user.getBio()),
				"users[0].email",
				 Matchers.notNullValue(),
				"users[0].email",
				 is(user.getEmail()),
				"users[0].id",
				 Matchers.notNullValue(),
				"users[0].id",
				 is(user.getId()),
				"users[0].image",
				 Matchers.notNullValue(),
				"users[0].image",
				 is(user.getImage()),
				"users[0].password",
				 Matchers.notNullValue(),
				"users[0].password",
				 is(user.getPassword()),
				"users[0].username",
				 Matchers.notNullValue(),
				"users[0].username",
				 is(user.getUsername())
				);
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bioXXX|eq|"+usersEntity.getBio()+
							""+"||"+"emailXXX|eq|"+usersEntity.getEmail()+
							""+"||"+"idXXX|eq|"+usersEntity.getId()+
							""+"||"+"imageXXX|eq|"+usersEntity.getImage()+
							""+"||"+"passwordXXX|eq|"+usersEntity.getPassword()+
							""+"||"+"usernameXXX|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","bio,email,id,image,password,username"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eqXXX|"+usersEntity.getBio()+
							""+"||"+"email|eqXXX|"+usersEntity.getEmail()+
							""+"||"+"id|eqXXX|"+usersEntity.getId()+
							""+"||"+"image|eqXXX|"+usersEntity.getImage()+
							""+"||"+"password|eqXXX|"+usersEntity.getPassword()+
							""+"||"+"username|eqXXX|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","bio,email,id,image,password,username"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							""+"||"+"email|eq|"+usersEntity.getEmail()+
							""+"||"+"id|eq|"+usersEntity.getId()+
							""+"||"+"image|eq|"+usersEntity.getImage()+
							""+"||"+"password|eq|"+usersEntity.getPassword()+
							""+"||"+"username|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","bio,email,id,image,password,username"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							""+"||"+"email|eq|"+usersEntity.getEmail()+
							""+"||"+"id|eq|"+usersEntity.getId()+
							""+"||"+"image|eq|"+usersEntity.getImage()+
							""+"||"+"password|eq|"+usersEntity.getPassword()+
							""+"||"+"username|eq|"+usersEntity.getUsername()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","bioXXX,emailXXX,idXXX,imageXXX,passwordXXX,usernameXXX"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Users_whenExecuteFindUsersByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usersEntity = createUsers("");
	    for (int i = 0; i < 9; i++) {
	    	createUsers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "bio|eq|"+usersEntity.getBio()+
							";:')"+"||"+"email|eq|"+usersEntity.getEmail()+
							";:')"+"||"+"id|eq|"+usersEntity.getId()+
							";:')"+"||"+"image|eq|"+usersEntity.getImage()+
							";:')"+"||"+"password|eq|"+usersEntity.getPassword()+
							";:')"+"||"+"username|eq|"+usersEntity.getUsername()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","bio,email,id,image,password,username"
	        )
	        .get(USERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
