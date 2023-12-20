// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
import firmansyah.application.web.model.request.NewTagsRequest;
import firmansyah.application.web.model.request.UpdateTagsRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TagsResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String TAGS_RESOURCE_PATH = API_PREFIX + "/firmansyah/tags";
  
	@Test
  	public void givenANewTags_whenExecuteCreateEndpoint_shouldReturnCreatedTags201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewTagsRequest tags = new NewTagsRequest();
			tags.setId("id");
			tags.setName("name");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(tags)).when()
		       .post(TAGS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"tags.id",
					 Matchers.notNullValue(),
					"tags.id",
					 is(tags.getId()),
					"tags.name",
					 Matchers.notNullValue(),
					"tags.name",
					 is(tags.getName())
					);
  	}
  
  	@Test
  	public void givenANewTagsWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewTagsRequest tags = new NewTagsRequest();
			tags.setId("id");
			tags.setName("name");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(tags))
        	.post(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidTags_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewTagsRequest tags = new NewTagsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tags))
        	.when()
        	.post(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1),
            	"errors.body",
            	hasItems(
						"id of Tags must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentTags_whenExecuteCreateEndpoint_shouldReturnConflictTagsRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var tagsEntity = createTags("");
		NewTagsRequest tags = new NewTagsRequest();
			tags.setId(tagsEntity.getId());
			tags.setName("name");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tags))
        	.post(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("tags already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentTags_whenExecuteUpdateEndpoint_shouldReturnUpdatedTags()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var tagsEntity = createTags("");
		UpdateTagsRequest tags = new UpdateTagsRequest();
			tags.setId(tagsEntity.getId());
			tags.setName("name");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tags))
        	.put(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"tags.id",
				 Matchers.notNullValue(),
				"tags.id",
				 is(tags.getId()),
				"tags.name",
				 Matchers.notNullValue(),
				"tags.name",
				 is(tags.getName())
        		);
        	
			Assertions.assertEquals(tags.getId(),
			 tagsEntity.getId());Assertions.assertEquals(tags.getName(),
			 tagsEntity.getName());
  	}
  
  	@Test
  	public void givenAExistentTagsWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var tagsEntity = createTags("");
		UpdateTagsRequest tags = new UpdateTagsRequest();
			tags.setId(tagsEntity.getId());
			tags.setName("name");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(tags))
        	.put(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentTags_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateTagsRequest tags = new UpdateTagsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tags))
        	.when()
        	.put(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"id of Tags must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentTags_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateTagsRequest tags = new UpdateTagsRequest();
			tags.setId("id");
			tags.setName("name");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(tags))
        	.when()
        	.put(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("tags not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentTags_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
 	        .delete(TAGS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentTags_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var tagsEntity = createTags("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", tagsEntity.getId())
 	        .delete(TAGS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findTagsEntityByPK(
	 tagsEntity.getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentTagsWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var tagsEntity = createTags("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", tagsEntity.getId())
        	.delete(TAGS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentTags_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var tagsEntity = createTags("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", tagsEntity.getId())
			.get(TAGS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"tags.id",
				 Matchers.notNullValue(),
				"tags.id",
				 is(tagsEntity.getId()),
				"tags.name",
				 Matchers.notNullValue(),
				"tags.name",
				 is(tagsEntity.getName())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentTags_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var tagsEntity = createTags("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
			.get(TAGS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentTagsWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var tagsEntity = createTags("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", tagsEntity.getId())
			.get(TAGS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"tags.id",
				 Matchers.notNullValue(),
				"tags.id",
				 is(tagsEntity.getId()),
				"tags.name",
				 Matchers.notNullValue(),
				"tags.name",
				 is(tagsEntity.getName())
				);
	}
   
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset0AndLimit5_shouldReturnListOf5Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tags[0]",
					 hasKey("id"),
					"tags[0]",
					 hasKey("name") ,
					"tagsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset0AndLimit10_shouldReturnListOf10Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tags[0]",
					 hasKey("id"),
					"tags[0]",
					 hasKey("name") ,
					"tagsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset0AndLimit15_shouldReturnListOf10Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tags[0]",
					 hasKey("id"),
					"tags[0]",
					 hasKey("name") ,
					"tagsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset1AndLimit5_shouldReturnListOf5Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"tags[0]",
					 hasKey("id"),
					"tags[0]",
					 hasKey("name") ,
					"tagsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset2AndLimit5_shouldReturnListOf0Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"tagsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset1AndLimit10_shouldReturnListOf0Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"tagsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilterWithOffset1AndLimit115_shouldReturnListOf0Tags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTags(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"tagsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							""+"||"+"name|eq|"+tagsEntity.getName()+
							""

	        )  
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 is(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 is(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							""+"||"+"name|eq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 is(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 is(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|neq|"+tagsEntity.getId()+
							""+"||"+"name|neq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 not(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 not(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|like|"+tagsEntity.getId()+
							""+"||"+"name|like|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 is(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 is(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|nlike|"+tagsEntity.getId()+
							""+"||"+"name|nlike|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 not(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 not(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							""+"||"+"name|eq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 is(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 is(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredTags() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							""+"||"+"name|eq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-id,-name"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"tags[0].id",
				 Matchers.notNullValue(),
				"tags[0].id",
				 is(tagsEntity.getId()),
				"tags[0].name",
				 Matchers.notNullValue(),
				"tags[0].name",
				 is(tagsEntity.getName())
				);
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "idXXX|eq|"+tagsEntity.getId()+
							""+"||"+"nameXXX|eq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eqXXX|"+tagsEntity.getId()+
							""+"||"+"name|eqXXX|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							""+"||"+"name|eq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							""+"||"+"name|eq|"+tagsEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","idXXX,nameXXX"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Tags_whenExecuteFindTagsByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var tagsEntity = createTags("");
	    for (int i = 0; i < 9; i++) {
	    	createTags(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+tagsEntity.getId()+
							";:')"+"||"+"name|eq|"+tagsEntity.getName()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(TAGS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
