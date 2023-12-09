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
import firmansyah.application.web.model.request.NewTagRelationshipRequest;
import firmansyah.application.web.model.request.UpdateTagRelationshipRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TagRelationshipResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String TAGRELATIONSHIP_RESOURCE_PATH = API_PREFIX + "/firmansyah/tagRelationship";
  
	@Test
  	public void givenANewTagRelationship_whenExecuteCreateEndpoint_shouldReturnCreatedTagRelationship201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewTagRelationshipRequest tagRelationship = new NewTagRelationshipRequest();
			final var tagsTagIdEntity= createTags("");
			tagRelationship.setTagId( tagsTagIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			tagRelationship.setArticleId( articlesArticleIdEntity.getId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(tagRelationship)).when()
		       .post(TAGRELATIONSHIP_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"tagRelationship.articlesArticleIdResponse.id",
					 Matchers.notNullValue(),
					"tagRelationship.articlesArticleIdResponse.id",
					 is( articlesArticleIdEntity.getId()),
					"tagRelationship.tagsTagIdResponse.id",
					 Matchers.notNullValue(),
					"tagRelationship.tagsTagIdResponse.id",
					 is( tagsTagIdEntity.getId())
					);
  	}
  
  	@Test
  	public void givenANewTagRelationshipWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewTagRelationshipRequest tagRelationship = new NewTagRelationshipRequest();
			final var tagsTagIdEntity= createTags("");
			tagRelationship.setTagId( tagsTagIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			tagRelationship.setArticleId( articlesArticleIdEntity.getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.post(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidTagRelationship_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewTagRelationshipRequest tagRelationship = new NewTagRelationshipRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.when()
        	.post(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"articleId of TagRelationship must not be blank",
						"tagId of TagRelationship must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentTagRelationship_whenExecuteCreateEndpoint_shouldReturnConflictTagRelationshipRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var tagRelationshipEntity = createTagRelationship("");
		NewTagRelationshipRequest tagRelationship = new NewTagRelationshipRequest();
			tagRelationship.setArticleId(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			tagRelationship.setTagId(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.post(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("tagrelationship already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentTagRelationship_whenExecuteUpdateEndpoint_shouldReturnUpdatedTagRelationship()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var tagRelationshipEntity = createTagRelationship("");
		UpdateTagRelationshipRequest tagRelationship = new UpdateTagRelationshipRequest();
			tagRelationship.setArticleId(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			tagRelationship.setTagId(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.put(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"tagRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship.articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship.tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship.tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
        		);
        	
			Assertions.assertEquals(tagRelationship.getArticleId(),
			 tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());Assertions.assertEquals(tagRelationship.getTagId(),
			 tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId());
  	}
  
  	@Test
  	public void givenAExistentTagRelationshipWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var tagRelationshipEntity = createTagRelationship("");
		UpdateTagRelationshipRequest tagRelationship = new UpdateTagRelationshipRequest();
			tagRelationship.setArticleId(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId());
			tagRelationship.setTagId(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.put(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentTagRelationship_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateTagRelationshipRequest tagRelationship = new UpdateTagRelationshipRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.when()
        	.put(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"articleId of TagRelationship must not be blank",
						"tagId of TagRelationship must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentTagRelationship_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateTagRelationshipRequest tagRelationship = new UpdateTagRelationshipRequest();
			final var tagsTagIdEntity= createTags("");
			tagRelationship.setTagId( tagsTagIdEntity.getId());
			final var articlesArticleIdEntity= createArticles("");
			tagRelationship.setArticleId( articlesArticleIdEntity.getId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.when()
        	.put(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("tagRelationship not found"));
  	}
  
  	@Test
  	public void givenANewTagRelationshipWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewTagRelationshipRequest tagRelationship = new NewTagRelationshipRequest();
			tagRelationship.setTagId("tagId");
			tagRelationship.setArticleId("articleId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tagRelationship))
        	.when()
        	.post(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("tags not found"),hasItems("articles not found")));
  	}
  
   	@Test
   	public void givenAExistentTagRelationshipWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var tagRelationshipEntity = createTagRelationship("");
		UpdateTagRelationshipRequest tagRelationship = new UpdateTagRelationshipRequest();
			tagRelationship.setArticleId("articleId");
			tagRelationship.setTagId("tagId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(tagRelationship))
         	.when()
         	.put(TAGRELATIONSHIP_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("tagRelationship not found"),hasItems("tags not found"),hasItems("articles not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentTagRelationship_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("articleId", "articleId")
			.queryParam("tagId", "tagId")
 	        .delete(TAGRELATIONSHIP_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentTagRelationship_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var tagRelationshipEntity = createTagRelationship("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("articleId", tagRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("tagId", tagRelationshipEntity.getTagsTagId().getId())
 	        .delete(TAGRELATIONSHIP_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findTagRelationshipEntityByPK(
		 tagRelationshipEntity.getArticlesArticleId().getId()
		,		     
		 tagRelationshipEntity.getTagsTagId().getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentTagRelationshipWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var tagRelationshipEntity = createTagRelationship("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		.queryParam("articleId", tagRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("tagId", tagRelationshipEntity.getTagsTagId().getId())
        	.delete(TAGRELATIONSHIP_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentTagRelationship_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var tagRelationshipEntity = createTagRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("articleId", tagRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("tagId", tagRelationshipEntity.getTagsTagId().getId())
			.get(TAGRELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"tagRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship.articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship.tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship.tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentTagRelationship_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var tagRelationshipEntity = createTagRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("articleId", "articleId")
			.queryParam("tagId", "tagId")
			.get(TAGRELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentTagRelationshipWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var tagRelationshipEntity = createTagRelationship("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		.queryParam("articleId", tagRelationshipEntity.getArticlesArticleId().getId())
		.queryParam("tagId", tagRelationshipEntity.getTagsTagId().getId())
			.get(TAGRELATIONSHIP_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"tagRelationship.articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship.articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship.tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship.tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
	}
   
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset0AndLimit5_shouldReturnListOf5TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tagRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"tagRelationship[0]",
					 hasKey("tagsTagIdResponse"),
					"tagRelationshipCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset0AndLimit10_shouldReturnListOf10TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tagRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"tagRelationship[0]",
					 hasKey("tagsTagIdResponse"),
					"tagRelationshipCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset0AndLimit15_shouldReturnListOf10TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tagRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"tagRelationship[0]",
					 hasKey("tagsTagIdResponse"),
					"tagRelationshipCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset1AndLimit5_shouldReturnListOf5TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tagRelationship[0]",
					 hasKey("articlesArticleIdResponse"),
					"tagRelationship[0]",
					 hasKey("tagsTagIdResponse"),
					"tagRelationshipCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset2AndLimit5_shouldReturnListOf0TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"tagRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset1AndLimit10_shouldReturnListOf0TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"tagRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilterWithOffset1AndLimit115_shouldReturnListOf0TagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"tagRelationshipCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|neq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|neq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 not(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 not(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|like|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|like|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|nlike|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|nlike|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 not(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 not(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,tagId"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredTagRelationship() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-articleId,-tagId"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tagRelationship[0].articlesArticleIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].articlesArticleIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getArticlesArticleId().getId()),
				"tagRelationship[0].tagsTagIdResponse.id",
				 Matchers.notNullValue(),
				"tagRelationship[0].tagsTagIdResponse.id",
				 is(tagRelationshipEntity.getPrimaryKey().getTagsTagId().getId())
				);
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleIdXXX|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagIdXXX|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,tagId"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eqXXX|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eqXXX|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,tagId"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","articleId,tagId"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							""+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleIdXXX,tagIdXXX"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10TagRelationship_whenExecuteFindTagRelationshipByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagRelationshipEntity = createTagRelationship("");
	    for (int i = 0; i < 9; i++) {
	    	createTagRelationship(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "articleId|eq|"+tagRelationshipEntity.getArticlesArticleId().getId()+
							";:')"+"||"+"tagId|eq|"+tagRelationshipEntity.getTagsTagId().getId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","articleId,tagId"
	        )
	        .get(TAGRELATIONSHIP_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
