package org.example.realworldapi.integration;

import static io.restassured.RestAssured.given;
import static org.example.realworldapi.utils.TestConstants.API_PREFIX;
import static org.example.realworldapi.utils.TestConstants.AUTHORIZATION_HEADER;
import static org.example.realworldapi.utils.TestConstants.AUTHORIZATION_HEADER_VALUE_PREFIX;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.anyOf;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.example.realworldapi.application.web.model.request.NewFavoriteRelationshipRequest;
import org.example.realworldapi.application.web.model.request.UpdateFavoriteRelationshipRequest;
import org.example.realworldapi.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FavoriteRelationshipResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String FAVORITERELATIONSHIP_RESOURCE_PATH = API_PREFIX + "/favoriteRelationship";
  
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
					"favoriteRelationship.usersUserIdResponse.id",
					 Matchers.notNullValue(),
					"favoriteRelationship.usersUserIdResponse.id",
					 is( usersUserIdEntity.getId()),
					"favoriteRelationship.articlesArticleIdResponse.id",
					 Matchers.notNullValue(),
					"favoriteRelationship.articlesArticleIdResponse.id",
					 is( articlesArticleIdEntity.getId())
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
						"userId of FavoriteRelationship must not be blank",
						"articleId of FavoriteRelationship must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentFavoriteRelationship_whenExecuteCreateEndpoint_shouldReturnConflictFavoriteRelationshipRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var favoriteRelationshipEntity = createFavoriteRelationship("");
		NewFavoriteRelationshipRequest favoriteRelationship = new NewFavoriteRelationshipRequest();
			favoriteRelationship.setUserId(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			favoriteRelationship.setArticleId(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			
			

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
			favoriteRelationship.setUserId(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			favoriteRelationship.setArticleId(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(favoriteRelationship))
        	.put(FAVORITERELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"favoriteRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
        		);
        	
			Assertions.assertEquals(favoriteRelationship.getUserId(),
			 favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());Assertions.assertEquals(favoriteRelationship.getArticleId(),
			 favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
  	}
  
  	@Test
  	public void givenAExistentFavoriteRelationshipWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var favoriteRelationshipEntity = createFavoriteRelationship("");
		UpdateFavoriteRelationshipRequest favoriteRelationship = new UpdateFavoriteRelationshipRequest();
			favoriteRelationship.setUserId(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId());
			favoriteRelationship.setArticleId(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			
	
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
            	hasItems("At least one field must be not null",							"userId of FavoriteRelationship must not be blank",
						"articleId of FavoriteRelationship must not be blank"
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
			favoriteRelationship.setUserId("userId");
			favoriteRelationship.setArticleId("articleId");
			
 	 
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
				"favoriteRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
				"favoriteRelationship.usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship.articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
					 hasKey("usersUserIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
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
					 hasKey("usersUserIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
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
					 hasKey("usersUserIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
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
					 hasKey("usersUserIdResponse"),
					"favoriteRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userId|neq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|neq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userId|like|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|like|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userId|nlike|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|nlike|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 not(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","userId,articleId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-userId,-articleId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"favoriteRelationship[0].usersUserIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].usersUserIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getUsersUserId().getId()),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"favoriteRelationship[0].articlesArticleIdResponse.id",
				 is(favoriteRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId())
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
	        .queryParam("filter", "userIdXXX|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleIdXXX|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","userId,articleId"
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
	        .queryParam("filter", "userId|eqXXX|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eqXXX|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","userId,articleId"
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","userId,articleId"
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							""+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","userIdXXX,articleIdXXX"
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
	        .queryParam("filter", "userId|eq|"+favoriteRelationshipEntity.getUsersUserId().getId()+
							";:')"+"||"+"articleId|eq|"+favoriteRelationshipEntity.getArticlesArticleId().getId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","userId,articleId"
	        )
	        .get(FAVORITERELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
