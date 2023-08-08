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
import org.example.realworldapi.application.web.model.request.NewArticlesRequest;
import org.example.realworldapi.application.web.model.request.UpdateArticlesRequest;
import org.example.realworldapi.utils.ResourcesIntegrationTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ArticlesResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String ARTICLES_RESOURCE_PATH = API_PREFIX + "/articles";
  
	@Test
  	public void givenANewArticles_whenExecuteCreateEndpoint_shouldReturnCreatedArticles201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewArticlesRequest articles = new NewArticlesRequest();
			articles.setId("id");
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			articles.setAuthorId( usersAuthorIdEntity.getId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(articles)).when()
		       .post(ARTICLES_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"articles.id",
					 Matchers.notNullValue(),
					"articles.id",
					 is(articles.getId()),
					"articles.body",
					 Matchers.notNullValue(),
					"articles.body",
					 is(articles.getBody()),
					"articles.description",
					 Matchers.notNullValue(),
					"articles.description",
					 is(articles.getDescription()),
					"articles.slug",
					 Matchers.notNullValue(),
					"articles.slug",
					 is(articles.getSlug()),
					"articles.title",
					 Matchers.notNullValue(),
					"articles.title",
					 is(articles.getTitle()),
					"articles.updatedat",
					 Matchers.notNullValue(),
					"articles.updatedat",
					 is(articles.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"articles.usersAuthorIdResponse.id",
					 Matchers.notNullValue(),
					"articles.usersAuthorIdResponse.id",
					 is( usersAuthorIdEntity.getId())
					);
  	}
  
  	@Test
  	public void givenANewArticlesWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewArticlesRequest articles = new NewArticlesRequest();
			articles.setId("id");
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			articles.setAuthorId( usersAuthorIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(articles))
        	.post(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidArticles_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewArticlesRequest articles = new NewArticlesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(articles))
        	.when()
        	.post(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"id of Articles must not be blank",
						"authorId of Articles must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentArticles_whenExecuteCreateEndpoint_shouldReturnConflictArticlesRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var articlesEntity = createArticles("");
		NewArticlesRequest articles = new NewArticlesRequest();
			articles.setId(articlesEntity.getId());
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			articles.setAuthorId( usersAuthorIdEntity.getId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(articles))
        	.post(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("articles already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentArticles_whenExecuteUpdateEndpoint_shouldReturnUpdatedArticles()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var articlesEntity = createArticles("");
		UpdateArticlesRequest articles = new UpdateArticlesRequest();
			articles.setId(articlesEntity.getId());
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			articles.setAuthorId( usersAuthorIdEntity.getId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(articles))
        	.put(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"articles.id",
				 Matchers.notNullValue(),
				"articles.id",
				 is(articles.getId()),
				"articles.body",
				 Matchers.notNullValue(),
				"articles.body",
				 is(articles.getBody()),
				"articles.description",
				 Matchers.notNullValue(),
				"articles.description",
				 is(articles.getDescription()),
				"articles.slug",
				 Matchers.notNullValue(),
				"articles.slug",
				 is(articles.getSlug()),
				"articles.title",
				 Matchers.notNullValue(),
				"articles.title",
				 is(articles.getTitle()),
				"articles.updatedat",
				 Matchers.notNullValue(),
				"articles.updatedat",
				 is(articles.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles.usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles.usersAuthorIdResponse.id",
				 is(usersAuthorIdEntity.getId())
        		);
        	
			Assertions.assertEquals(articles.getId(),
			 articlesEntity.getId());Assertions.assertEquals(articles.getBody(),
			 articlesEntity.getBody());Assertions.assertEquals(articles.getDescription(),
			 articlesEntity.getDescription());Assertions.assertEquals(articles.getSlug(),
			 articlesEntity.getSlug());Assertions.assertEquals(articles.getTitle(),
			 articlesEntity.getTitle());Assertions.assertEquals(articles.getAuthorId(),
			 usersAuthorIdEntity.getId());
  	}
  
  	@Test
  	public void givenAExistentArticlesWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var articlesEntity = createArticles("");
		UpdateArticlesRequest articles = new UpdateArticlesRequest();
			articles.setId(articlesEntity.getId());
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			articles.setAuthorId( usersAuthorIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(articles))
        	.put(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentArticles_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateArticlesRequest articles = new UpdateArticlesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(articles))
        	.when()
        	.put(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"id of Articles must not be blank",
						"authorId of Articles must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentArticles_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateArticlesRequest articles = new UpdateArticlesRequest();
			articles.setId("id");
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			final var usersAuthorIdEntity= createUsers("");
			articles.setAuthorId( usersAuthorIdEntity.getId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(articles))
        	.when()
        	.put(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("articles not found"));
  	}
  
  	@Test
  	public void givenANewArticlesWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewArticlesRequest articles = new NewArticlesRequest();
			articles.setId("id");
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			articles.setAuthorId("authorId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(articles))
        	.when()
        	.post(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("users not found")));
  	}
  
   	@Test
   	public void givenAExistentArticlesWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var articlesEntity = createArticles("");
		UpdateArticlesRequest articles = new UpdateArticlesRequest();
			articles.setId(articlesEntity.getId());
			articles.setBody("body");
			articles.setDescription("description");
			articles.setSlug("slug");
			articles.setTitle("title");
			articles.setUpdatedat(LocalDateTime.now());
			articles.setAuthorId("authorId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(articles))
         	.when()
         	.put(ARTICLES_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("articles not found"),hasItems("users not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentArticles_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
 	        .delete(ARTICLES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentArticles_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var articlesEntity = createArticles("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", articlesEntity.getId())
 	        .delete(ARTICLES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findArticlesEntityByPK(
	 articlesEntity.getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentArticlesWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var articlesEntity = createArticles("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", articlesEntity.getId())
        	.delete(ARTICLES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentArticles_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var articlesEntity = createArticles("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", articlesEntity.getId())
			.get(ARTICLES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"articles.id",
				 Matchers.notNullValue(),
				"articles.id",
				 is(articlesEntity.getId()),
				"articles.body",
				 Matchers.notNullValue(),
				"articles.body",
				 is(articlesEntity.getBody()),
				"articles.description",
				 Matchers.notNullValue(),
				"articles.description",
				 is(articlesEntity.getDescription()),
				"articles.slug",
				 Matchers.notNullValue(),
				"articles.slug",
				 is(articlesEntity.getSlug()),
				"articles.title",
				 Matchers.notNullValue(),
				"articles.title",
				 is(articlesEntity.getTitle()),
				"articles.updatedat",
				 Matchers.notNullValue(),
				"articles.updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles.usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles.usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentArticles_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var articlesEntity = createArticles("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
			.get(ARTICLES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentArticlesWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var articlesEntity = createArticles("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", articlesEntity.getId())
			.get(ARTICLES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"articles.id",
				 Matchers.notNullValue(),
				"articles.id",
				 is(articlesEntity.getId()),
				"articles.body",
				 Matchers.notNullValue(),
				"articles.body",
				 is(articlesEntity.getBody()),
				"articles.description",
				 Matchers.notNullValue(),
				"articles.description",
				 is(articlesEntity.getDescription()),
				"articles.slug",
				 Matchers.notNullValue(),
				"articles.slug",
				 is(articlesEntity.getSlug()),
				"articles.title",
				 Matchers.notNullValue(),
				"articles.title",
				 is(articlesEntity.getTitle()),
				"articles.updatedat",
				 Matchers.notNullValue(),
				"articles.updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles.usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles.usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
	}
   
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset0AndLimit5_shouldReturnListOf5Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"articles[0]",
					 hasKey("id"),
					"articles[0]",
					 hasKey("body"),
					"articles[0]",
					 hasKey("description"),
					"articles[0]",
					 hasKey("slug"),
					"articles[0]",
					 hasKey("title"),
					"articles[0]",
					 hasKey("updatedat"),
					"articles[0]",
					 hasKey("usersAuthorIdResponse"),
					"articlesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset0AndLimit10_shouldReturnListOf10Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"articles[0]",
					 hasKey("id"),
					"articles[0]",
					 hasKey("body"),
					"articles[0]",
					 hasKey("description"),
					"articles[0]",
					 hasKey("slug"),
					"articles[0]",
					 hasKey("title"),
					"articles[0]",
					 hasKey("updatedat"),
					"articles[0]",
					 hasKey("usersAuthorIdResponse"),
					"articlesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset0AndLimit15_shouldReturnListOf10Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"articles[0]",
					 hasKey("id"),
					"articles[0]",
					 hasKey("body"),
					"articles[0]",
					 hasKey("description"),
					"articles[0]",
					 hasKey("slug"),
					"articles[0]",
					 hasKey("title"),
					"articles[0]",
					 hasKey("updatedat"),
					"articles[0]",
					 hasKey("usersAuthorIdResponse"),
					"articlesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset1AndLimit5_shouldReturnListOf5Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"articles[0]",
					 hasKey("id"),
					"articles[0]",
					 hasKey("body"),
					"articles[0]",
					 hasKey("description"),
					"articles[0]",
					 hasKey("slug"),
					"articles[0]",
					 hasKey("title"),
					"articles[0]",
					 hasKey("updatedat"),
					"articles[0]",
					 hasKey("usersAuthorIdResponse"),
					"articlesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset2AndLimit5_shouldReturnListOf0Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"articlesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset1AndLimit10_shouldReturnListOf0Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"articlesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilterWithOffset1AndLimit115_shouldReturnListOf0Articles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createArticles(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"articlesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							""+"||"+"body|eq|"+articlesEntity.getBody()+
							""+"||"+"description|eq|"+articlesEntity.getDescription()+
							""+"||"+"slug|eq|"+articlesEntity.getSlug()+
							""+"||"+"title|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 is(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 is(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 is(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 is(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 is(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							""+"||"+"body|eq|"+articlesEntity.getBody()+
							""+"||"+"description|eq|"+articlesEntity.getDescription()+
							""+"||"+"slug|eq|"+articlesEntity.getSlug()+
							""+"||"+"title|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 is(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 is(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 is(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 is(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 is(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|neq|"+articlesEntity.getId()+
							""+"||"+"body|neq|"+articlesEntity.getBody()+
							""+"||"+"description|neq|"+articlesEntity.getDescription()+
							""+"||"+"slug|neq|"+articlesEntity.getSlug()+
							""+"||"+"title|neq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|neq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|neq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 not(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 not(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 not(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 not(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 not(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 not(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 not(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|like|"+articlesEntity.getId()+
							""+"||"+"body|like|"+articlesEntity.getBody()+
							""+"||"+"description|like|"+articlesEntity.getDescription()+
							""+"||"+"slug|like|"+articlesEntity.getSlug()+
							""+"||"+"title|like|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|like|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|like|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND,AND"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 is(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 is(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 is(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 is(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 is(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|nlike|"+articlesEntity.getId()+
							""+"||"+"body|nlike|"+articlesEntity.getBody()+
							""+"||"+"description|nlike|"+articlesEntity.getDescription()+
							""+"||"+"slug|nlike|"+articlesEntity.getSlug()+
							""+"||"+"title|nlike|"+articlesEntity.getTitle()+
							""+"||"+"authorId|nlike|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 not(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 not(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 not(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 not(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 not(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 not(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 not(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							""+"||"+"body|eq|"+articlesEntity.getBody()+
							""+"||"+"description|eq|"+articlesEntity.getDescription()+
							""+"||"+"slug|eq|"+articlesEntity.getSlug()+
							""+"||"+"title|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,description,slug,title,updatedat,authorId"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 is(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 is(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 is(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 is(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 is(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredArticles() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							""+"||"+"body|eq|"+articlesEntity.getBody()+
							""+"||"+"description|eq|"+articlesEntity.getDescription()+
							""+"||"+"slug|eq|"+articlesEntity.getSlug()+
							""+"||"+"title|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-id,-body,-description,-slug,-title,-updatedat,-authorId"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"articles[0].id",
				 Matchers.notNullValue(),
				"articles[0].id",
				 is(articlesEntity.getId()),
				"articles[0].body",
				 Matchers.notNullValue(),
				"articles[0].body",
				 is(articlesEntity.getBody()),
				"articles[0].description",
				 Matchers.notNullValue(),
				"articles[0].description",
				 is(articlesEntity.getDescription()),
				"articles[0].slug",
				 Matchers.notNullValue(),
				"articles[0].slug",
				 is(articlesEntity.getSlug()),
				"articles[0].title",
				 Matchers.notNullValue(),
				"articles[0].title",
				 is(articlesEntity.getTitle()),
				"articles[0].updatedat",
				 Matchers.notNullValue(),
				"articles[0].updatedat",
				 is(articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"articles[0].usersAuthorIdResponse.id",
				 Matchers.notNullValue(),
				"articles[0].usersAuthorIdResponse.id",
				 is(articlesEntity.getUsersAuthorId().getId())
				);
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "idXXX|eq|"+articlesEntity.getId()+
							""+"||"+"bodyXXX|eq|"+articlesEntity.getBody()+
							""+"||"+"descriptionXXX|eq|"+articlesEntity.getDescription()+
							""+"||"+"slugXXX|eq|"+articlesEntity.getSlug()+
							""+"||"+"titleXXX|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedatXXX|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorIdXXX|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,description,slug,title,updatedat,authorId"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eqXXX|"+articlesEntity.getId()+
							""+"||"+"body|eqXXX|"+articlesEntity.getBody()+
							""+"||"+"description|eqXXX|"+articlesEntity.getDescription()+
							""+"||"+"slug|eqXXX|"+articlesEntity.getSlug()+
							""+"||"+"title|eqXXX|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eqXXX|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eqXXX|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,description,slug,title,updatedat,authorId"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							""+"||"+"body|eq|"+articlesEntity.getBody()+
							""+"||"+"description|eq|"+articlesEntity.getDescription()+
							""+"||"+"slug|eq|"+articlesEntity.getSlug()+
							""+"||"+"title|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","id,body,description,slug,title,updatedat,authorId"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							""+"||"+"body|eq|"+articlesEntity.getBody()+
							""+"||"+"description|eq|"+articlesEntity.getDescription()+
							""+"||"+"slug|eq|"+articlesEntity.getSlug()+
							""+"||"+"title|eq|"+articlesEntity.getTitle()+
							""+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","idXXX,bodyXXX,descriptionXXX,slugXXX,titleXXX,updatedatXXX,authorIdXXX"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Articles_whenExecuteFindArticlesByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var articlesEntity = createArticles("");
	    for (int i = 0; i < 9; i++) {
	    	createArticles(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+articlesEntity.getId()+
							";:')"+"||"+"body|eq|"+articlesEntity.getBody()+
							";:')"+"||"+"description|eq|"+articlesEntity.getDescription()+
							";:')"+"||"+"slug|eq|"+articlesEntity.getSlug()+
							";:')"+"||"+"title|eq|"+articlesEntity.getTitle()+
							";:')"+"||"+"updatedat|eq|"+articlesEntity.getUpdatedat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"authorId|eq|"+articlesEntity.getUsersAuthorId().getId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","id,body,description,slug,title,updatedat,authorId"
	        )
	        .get(ARTICLES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
