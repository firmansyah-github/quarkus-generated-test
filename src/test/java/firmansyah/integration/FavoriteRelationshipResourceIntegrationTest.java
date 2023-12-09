// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
import firmansyah.application.web.model.request.NewFavoriteRelationshipRequest;
import firmansyah.application.web.model.request.UpdateFavoriteRelationshipRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FavoriteRelationshipResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String FAVORITERELATIONSHIP_RESOURCE_PATH = API_PREFIX + "/firmansyah/favoriteRelationship";
  
	@Test
  	public void givenANewFavoriteRelationship_whenExecuteCreateEndpoint_shouldReturnCreatedFavoriteRelationship201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewFavoriteRelationshipRequest favoriteRelationship = new NewFavoriteRelationshipRequest();
			final var usersUserIdEntity= createUsers("");
			favoriteRelationship.setUserId( usersUserIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			favoriteRelationship.setArticleId( articlesArticleIdEntity.getId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(favoriteRelationship)).when()
		       .post(FAVORITERELATIONSHIP_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"favoriteRelationship.articlesArticleIdResponse.id",
					 Matchers.notNullValue(),
					"favoriteRelationship.articlesArticleIdResponse.id",
					 is( articlesArticleIdEntity.getId()),
					"favoriteRelationship.usersUserIdResponse.id",
					 Matchers.notNullValue(),
					"favoriteRelationship.usersUserIdResponse.id",
					 is( usersUserIdEntity.getId())
					);
  	}
  
  	@Test
  	public void givenANewFavoriteRelationshipWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewFavoriteRelationshipRequest favoriteRelationship = new NewFavoriteRelationshipRequest();
			final var usersUserIdEntity= createUsers("");
			favoriteRelationship.setUserId( usersUserIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			favoriteRelationship.setArticleId( articlesArticleIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.post(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidFavoriteRelationship_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewFavoriteRelationshipRequest favoriteRelationship = new NewFavoriteRelationshipRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.when()
        	.post(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"articleId of FavoriteRelationship must not be blank",
						"userId of FavoriteRelationship must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentFavoriteRelationship_whenExecuteCreateEndpoint_shouldReturnConflictFavoriteRelationshipRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var favoriteRelationshipEntity = createFavoriteRelationship("");
		NewFavoriteRelationshipRequest favoriteRelationship = new NewFavoriteRelationshipRequest();
			favoriteRelationship.setArticleId(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			favoriteRelationship.setUserId(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.post(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("favoriterelationship already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentFavoriteRelationship_whenExecuteUpdateEndpoint_shouldReturnUpdatedFavoriteRelationship()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var favoriteRelationshipEntity = createFavoriteRelationship("");
		UpdateFavoriteRelationshipRequest favoriteRelationship = new UpdateFavoriteRelationshipRequest();
			favoriteRelationship.setArticleId(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			favoriteRelationship.setUserId(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.put(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"favoriteRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
        		);
        	
			Assertions.assertEquals(favoriteRelationship.getArticleId(),
			 favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());Assertions.assertEquals(favoriteRelationship.getUserId(),
			 favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
  	}
  
  	@Test
  	public void givenAExistentFavoriteRelationshipWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var favoriteRelationshipEntity = createFavoriteRelationship("");
		UpdateFavoriteRelationshipRequest favoriteRelationship = new UpdateFavoriteRelationshipRequest();
			favoriteRelationship.setArticleId(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			favoriteRelationship.setUserId(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.put(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentFavoriteRelationship_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateFavoriteRelationshipRequest favoriteRelationship = new UpdateFavoriteRelationshipRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.when()
        	.put(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"articleId of FavoriteRelationship must not be blank",
						"userId of FavoriteRelationship must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentFavoriteRelationship_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateFavoriteRelationshipRequest favoriteRelationship = new UpdateFavoriteRelationshipRequest();
			final var usersUserIdEntity= createUsers("");
			favoriteRelationship.setUserId( usersUserIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			favoriteRelationship.setArticleId( articlesArticleIdEntity.getId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.when()
        	.put(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("favoriteRelationship not found"));
  	}
  
  	@Test
  	public void givenANewFavoriteRelationshipWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewFavoriteRelationshipRequest favoriteRelationship = new NewFavoriteRelationshipRequest();
			favoriteRelationship.setUserId("userId");
			favoriteRelationship.setArticleId("articleId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.when()
        	.post(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("users not found"),hasItems("articles not found")));
  	}
  
   	@Test
   	public void givenAExistentFavoriteRelationshipWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var favoriteRelationshipEntity = createFavoriteRelationship("");
		UpdateFavoriteRelationshipRequest favoriteRelationship = new UpdateFavoriteRelationshipRequest();
			favoriteRelationship.setArticleId("articleId");
			favoriteRelationship.setUserId("userId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(favoriteRelationship))
         	.when()
         	.put(FAVORITERELATIONSHIP_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("favoriteRelationship not found"),hasItems("users not found"),hasItems("articles not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentFavoriteRelationship_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("articleId", "articleId")
			.queryParam("userId", "userId")
 	        .delete(FAVORITERELATIONSHIP_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentFavoriteRelationship_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var favoriteRelationshipEntity = createFavoriteRelationship("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("articleId", favoriteRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("userId", favoriteRelationshipEntity.getUsersUserId().getId())
 	        .delete(FAVORITERELATIONSHIP_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findFavoriteRelationshipEntityByPK(
		 favoriteRelationshipEntity.getArticlesArticleId().getId()
		,		     
		 favoriteRelationshipEntity.getUsersUserId().getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentFavoriteRelationshipWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var favoriteRelationshipEntity = createFavoriteRelationship("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		.queryParam("articleId", favoriteRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("userId", favoriteRelationshipEntity.getUsersUserId().getId())
        	.delete(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentFavoriteRelationship_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var favoriteRelationshipEntity = createFavoriteRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("articleId", favoriteRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("userId", favoriteRelationshipEntity.getUsersUserId().getId())
			.get(FAVORITERELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"favoriteRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentFavoriteRelationship_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var favoriteRelationshipEntity = createFavoriteRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("articleId", "articleId")
			.queryParam("userId", "userId")
			.get(FAVORITERELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentFavoriteRelationshipWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var favoriteRelationshipEntity = createFavoriteRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		.queryParam("articleId", favoriteRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("userId", favoriteRelationshipEntity.getUsersUserId().getId())
			.get(FAVORITERELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"favoriteRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
	}
   
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset0AndLimit5_shouldReturnListOf5FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"favoriteRelationshipCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset0AndLimit10_shouldReturnListOf10FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"favoriteRelationshipCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset0AndLimit15_shouldReturnListOf10FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"favoriteRelationshipCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset1AndLimit5_shouldReturnListOf5FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("usersUserIdResponse"),
					"favoriteRelationshipCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset2AndLimit5_shouldReturnListOf0FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"favoriteRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset1AndLimit10_shouldReturnListOf0FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"favoriteRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilterWithOffset1AndLimit115_shouldReturnListOf0FavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"favoriteRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|neq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|neq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|like|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|like|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|nlike|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|nlike|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,userId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredFavoriteRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-articleId,-userId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId())
				);
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleIdXXX|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userIdXXX|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,userId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eqXXX|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eqXXX|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,userId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","articleId,userId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleIdXXX,userIdXXX"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10FavoriteRelationship_whenExecuteFindFavoriteRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var favoriteRelationshipEntity = createFavoriteRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createFavoriteRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							";:')"+"||"+"userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,userId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
