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
import org.example.realworldapi.application.web.model.request.NewCommentsRequest;
import org.example.realworldapi.application.web.model.request.UpdateCommentsRequest;
import org.example.realworldapi.utils.ResourcesIntegrationTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CommentsResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String COMMENTS_RESOURCE_PATH = API_PREFIX + "/comments";
  
	@Test
  	public void givenANewComments_whenExecuteCreateEndpoint_shouldReturnCreatedComments201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewCommentsRequest comments = new NewCommentsRequest();
			comments.setId("id");
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			comments.setAuthorId( usersAuthorIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			comments.setArticleId( articlesArticleIdEntity.getId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(comments)).when()
		       .post(COMMENTS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"comments.id",
					 Matchers.notNullValue(),
					"comments.id",
					 is(comments.getId()),
					"comments.body",
					 Matchers.notNullValue(),
					"comments.body",
					 is(comments.getBody()),
					"comments.createdat",
					 Matchers.notNullValue(),
					"comments.createdat",
					 is(comments.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"comments.updatedat",
					 Matchers.notNullValue(),
					"comments.updatedat",
					 is(comments.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"comments.articlesArticleIdResponse.id",
					 Matchers.notNullValue(),
					"comments.articlesArticleIdResponse.id",
					 is( articlesArticleIdEntity.getId()),
					"comments.usersAuthorIdResponse.id",
					 Matchers.notNullValue(),
					"comments.usersAuthorIdResponse.id",
					 is( usersAuthorIdEntity.getId())
					);
  	}
  
  	@Test
  	public void givenANewCommentsWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewCommentsRequest comments = new NewCommentsRequest();
			comments.setId("id");
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			comments.setAuthorId( usersAuthorIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			comments.setArticleId( articlesArticleIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(comments))
        	.post(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidComments_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewCommentsRequest comments = new NewCommentsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(comments))
        	.when()
        	.post(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3),
            	"errors.body",
            	hasItems(
						"id of Comments must not be blank",
						"articleId of Comments must not be blank",
						"authorId of Comments must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentComments_whenExecuteCreateEndpoint_shouldReturnConflictCommentsRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var commentsEntity = createComments("");
		NewCommentsRequest comments = new NewCommentsRequest();
			comments.setId(commentsEntity.getId());
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			final var articlesArticleIdEntity= createArticles("");
			comments.setArticleId( articlesArticleIdEntity.getId());
			final var usersAuthorIdEntity= createUsers("");
			comments.setAuthorId( usersAuthorIdEntity.getId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(comments))
        	.post(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("comments already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentComments_whenExecuteUpdateEndpoint_shouldReturnUpdatedComments()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var commentsEntity = createComments("");
		UpdateCommentsRequest comments = new UpdateCommentsRequest();
			comments.setId(commentsEntity.getId());
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			final var articlesArticleIdEntity= createArticles("");
			comments.setArticleId( articlesArticleIdEntity.getId());
			final var usersAuthorIdEntity= createUsers("");
			comments.setAuthorId( usersAuthorIdEntity.getId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(comments))
        	.put(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"comments.id",
				 Matchers.notNullValue(),
				"comments.id",
				 is(comments.getId()),
				"comments.body",
				 Matchers.notNullValue(),
				"comments.body",
				 is(comments.getBody()),
				"comments.createdat",
				 Matchers.notNullValue(),
				"comments.createdat",
				 is(comments.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments.updatedat",
				 Matchers.notNullValue(),
				"comments.updatedat",
				 is(comments.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments.articlesArticleIdResponse.id",
				 is(articlesArticleIdEntity.getId()),
				"comments.usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments.usersAuthorIdResponse.id",
				 is(usersAuthorIdEntity.getId())
        		);
        	
			Assertions.assertEquals(comments.getId(),
			 commentsEntity.getId());Assertions.assertEquals(comments.getBody(),
			 commentsEntity.getBody());Assertions.assertEquals(comments.getArticleId(),
			 articlesArticleIdEntity.getId());Assertions.assertEquals(comments.getAuthorId(),
			 usersAuthorIdEntity.getId());
  	}
  
  	@Test
  	public void givenAExistentCommentsWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var commentsEntity = createComments("");
		UpdateCommentsRequest comments = new UpdateCommentsRequest();
			comments.setId(commentsEntity.getId());
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			final var articlesArticleIdEntity= createArticles("");
			comments.setArticleId( articlesArticleIdEntity.getId());
			final var usersAuthorIdEntity= createUsers("");
			comments.setAuthorId( usersAuthorIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(comments))
        	.put(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentComments_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateCommentsRequest comments = new UpdateCommentsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(comments))
        	.when()
        	.put(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"id of Comments must not be blank",
						"articleId of Comments must not be blank",
						"authorId of Comments must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentComments_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateCommentsRequest comments = new UpdateCommentsRequest();
			comments.setId("id");
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			comments.setAuthorId( usersAuthorIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			comments.setArticleId( articlesArticleIdEntity.getId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(comments))
        	.when()
        	.put(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("comments not found"));
  	}
  
  	@Test
  	public void givenANewCommentsWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewCommentsRequest comments = new NewCommentsRequest();
			comments.setId("id");
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			comments.setAuthorId("authorId");
			comments.setArticleId("articleId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(comments))
        	.when()
        	.post(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("users not found"),hasItems("articles not found")));
  	}
  
   	@Test
   	public void givenAExistentCommentsWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var commentsEntity = createComments("");
		UpdateCommentsRequest comments = new UpdateCommentsRequest();
			comments.setId(commentsEntity.getId());
			comments.setBody("body");
			comments.setCreatedat(LocalDateTime.now());
			comments.setUpdatedat(LocalDateTime.now());
			comments.setArticleId("articleId");
			comments.setAuthorId("authorId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(comments))
         	.when()
         	.put(COMMENTS_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("comments not found"),hasItems("users not found"),hasItems("articles not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentComments_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
 	        .delete(COMMENTS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentComments_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var commentsEntity = createComments("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", commentsEntity.getId())
 	        .delete(COMMENTS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findCommentsEntityByPK(
	 commentsEntity.getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentCommentsWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var commentsEntity = createComments("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", commentsEntity.getId())
        	.delete(COMMENTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentComments_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var commentsEntity = createComments("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", commentsEntity.getId())
			.get(COMMENTS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"comments.id",
				 Matchers.notNullValue(),
				"comments.id",
				 is(commentsEntity.getId()),
				"comments.body",
				 Matchers.notNullValue(),
				"comments.body",
				 is(commentsEntity.getBody()),
				"comments.createdat",
				 Matchers.notNullValue(),
				"comments.createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments.updatedat",
				 Matchers.notNullValue(),
				"comments.updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments.articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments.usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments.usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentComments_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var commentsEntity = createComments("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
			.get(COMMENTS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentCommentsWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var commentsEntity = createComments("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", commentsEntity.getId())
			.get(COMMENTS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"comments.id",
				 Matchers.notNullValue(),
				"comments.id",
				 is(commentsEntity.getId()),
				"comments.body",
				 Matchers.notNullValue(),
				"comments.body",
				 is(commentsEntity.getBody()),
				"comments.createdat",
				 Matchers.notNullValue(),
				"comments.createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments.updatedat",
				 Matchers.notNullValue(),
				"comments.updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments.articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments.usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments.usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
	}
   
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset0AndLimit5_shouldReturnListOf5Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"comments[0]",
					 hasKey("id"),
					"comments[0]",
					 hasKey("body"),
					"comments[0]",
					 hasKey("createdat"),
					"comments[0]",
					 hasKey("updatedat"),
					"comments[0]",
					 hasKey("articlesArticleIdResponse"),
					"comments[0]",
					 hasKey("usersAuthorIdResponse"),
					"commentsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset0AndLimit10_shouldReturnListOf10Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"comments[0]",
					 hasKey("id"),
					"comments[0]",
					 hasKey("body"),
					"comments[0]",
					 hasKey("createdat"),
					"comments[0]",
					 hasKey("updatedat"),
					"comments[0]",
					 hasKey("articlesArticleIdResponse"),
					"comments[0]",
					 hasKey("usersAuthorIdResponse"),
					"commentsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset0AndLimit15_shouldReturnListOf10Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"comments[0]",
					 hasKey("id"),
					"comments[0]",
					 hasKey("body"),
					"comments[0]",
					 hasKey("createdat"),
					"comments[0]",
					 hasKey("updatedat"),
					"comments[0]",
					 hasKey("articlesArticleIdResponse"),
					"comments[0]",
					 hasKey("usersAuthorIdResponse"),
					"commentsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset1AndLimit5_shouldReturnListOf5Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"comments[0]",
					 hasKey("id"),
					"comments[0]",
					 hasKey("body"),
					"comments[0]",
					 hasKey("createdat"),
					"comments[0]",
					 hasKey("updatedat"),
					"comments[0]",
					 hasKey("articlesArticleIdResponse"),
					"comments[0]",
					 hasKey("usersAuthorIdResponse"),
					"commentsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset2AndLimit5_shouldReturnListOf0Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"commentsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset1AndLimit10_shouldReturnListOf0Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"commentsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilterWithOffset1AndLimit115_shouldReturnListOf0Comments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createComments(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"commentsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							""+"||"+"body|eq|"+commentsEntity.getBody()+
							""+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 is(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 is(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							""+"||"+"body|eq|"+commentsEntity.getBody()+
							""+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 is(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 is(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|neq|"+commentsEntity.getId()+
							""+"||"+"body|neq|"+commentsEntity.getBody()+
							""+"||"+"createdat|neq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|neq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|neq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|neq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 not(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 not(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 not(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 not(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 not(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 not(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|like|"+commentsEntity.getId()+
							""+"||"+"body|like|"+commentsEntity.getBody()+
							""+"||"+"createdat|like|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|like|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|like|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|like|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 is(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 is(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|nlike|"+commentsEntity.getId()+
							""+"||"+"body|nlike|"+commentsEntity.getBody()+
							""+"||"+"articleId|nlike|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|nlike|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 not(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 not(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 not(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 not(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 not(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 not(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							""+"||"+"body|eq|"+commentsEntity.getBody()+
							""+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,createdat,updatedat,articleId,authorId"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 is(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 is(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredComments() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							""+"||"+"body|eq|"+commentsEntity.getBody()+
							""+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-id,-body,-createdat,-updatedat,-articleId,-authorId"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"comments[0].id",
				 Matchers.notNullValue(),
				"comments[0].id",
				 is(commentsEntity.getId()),
				"comments[0].body",
				 Matchers.notNullValue(),
				"comments[0].body",
				 is(commentsEntity.getBody()),
				"comments[0].createdat",
				 Matchers.notNullValue(),
				"comments[0].createdat",
				 is(commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].updatedat",
				 Matchers.notNullValue(),
				"comments[0].updatedat",
				 is(commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"comments[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].articlesArticleIdResponse.id",
				 is(commentsEntity.getArticlesArticleId().getId()),
				"comments[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"comments[0].usersAuthorIdResponse.id",
				 is(commentsEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "idXXX|eq|"+commentsEntity.getId()+
							""+"||"+"bodyXXX|eq|"+commentsEntity.getBody()+
							""+"||"+"createdatXXX|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedatXXX|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleIdXXX|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorIdXXX|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,createdat,updatedat,articleId,authorId"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eqXXX|"+commentsEntity.getId()+
							""+"||"+"body|eqXXX|"+commentsEntity.getBody()+
							""+"||"+"createdat|eqXXX|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eqXXX|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eqXXX|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eqXXX|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,createdat,updatedat,articleId,authorId"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							""+"||"+"body|eq|"+commentsEntity.getBody()+
							""+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","id,body,createdat,updatedat,articleId,authorId"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							""+"||"+"body|eq|"+commentsEntity.getBody()+
							""+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							""+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","idXXX,bodyXXX,createdatXXX,updatedatXXX,articleIdXXX,authorIdXXX"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Comments_whenExecuteFindCommentsByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var commentsEntity = createComments("");
	    for (int i = 0; i < 9; i++) {
	    	createComments(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+commentsEntity.getId()+
							";:')"+"||"+"body|eq|"+commentsEntity.getBody()+
							";:')"+"||"+"createdat|eq|"+commentsEntity.getCreatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"updatedat|eq|"+commentsEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"articleId|eq|"+commentsEntity.getArticlesArticleId().getId()+
							";:')"+"||"+"authorId|eq|"+commentsEntity.getUsersAuthorId().getId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,createdat,updatedat,articleId,authorId"
	        )
	        .get(COMMENTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
