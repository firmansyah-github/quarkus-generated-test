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
import org.example.realworldapi.application.web.model.request.NewSchoolRequest;
import org.example.realworldapi.application.web.model.request.UpdateSchoolRequest;
import org.example.realworldapi.utils.ResourcesIntegrationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SchoolResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String SCHOOL_RESOURCE_PATH = API_PREFIX + "/school";
  
	@Test
  	public void givenANewSchool_whenExecuteCreateEndpoint_shouldReturnCreatedSchool201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewSchoolRequest school = new NewSchoolRequest();
			school.setId("id");
			school.setName("name");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(school)).when()
		       .post(SCHOOL_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"school.id",
					 Matchers.notNullValue(),
					"school.id",
					 is(school.getId()),
					"school.name",
					 Matchers.notNullValue(),
					"school.name",
					 is(school.getName())
					);
  	}
  
  	@Test
  	public void givenANewSchoolWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewSchoolRequest school = new NewSchoolRequest();
			school.setId("id");
			school.setName("name");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(school))
        	.post(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidSchool_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewSchoolRequest school = new NewSchoolRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(school))
        	.when()
        	.post(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1),
            	"errors.body",
            	hasItems(
						"id of School must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentSchool_whenExecuteCreateEndpoint_shouldReturnConflictSchoolRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var schoolEntity = createSchool("");
		NewSchoolRequest school = new NewSchoolRequest();
			school.setId(schoolEntity.getId());
			school.setName("name");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(school))
        	.post(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("school already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentSchool_whenExecuteUpdateEndpoint_shouldReturnUpdatedSchool()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var schoolEntity = createSchool("");
		UpdateSchoolRequest school = new UpdateSchoolRequest();
			school.setId(schoolEntity.getId());
			school.setName("name");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(school))
        	.put(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"school.id",
				 Matchers.notNullValue(),
				"school.id",
				 is(school.getId()),
				"school.name",
				 Matchers.notNullValue(),
				"school.name",
				 is(school.getName())
        		);
        	
			Assertions.assertEquals(school.getId(),
			 schoolEntity.getId());Assertions.assertEquals(school.getName(),
			 schoolEntity.getName());
  	}
  
  	@Test
  	public void givenAExistentSchoolWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var schoolEntity = createSchool("");
		UpdateSchoolRequest school = new UpdateSchoolRequest();
			school.setId(schoolEntity.getId());
			school.setName("name");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(school))
        	.put(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentSchool_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateSchoolRequest school = new UpdateSchoolRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(school))
        	.when()
        	.put(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"id of School must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentSchool_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateSchoolRequest school = new UpdateSchoolRequest();
			school.setId("id");
			school.setName("name");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(school))
        	.when()
        	.put(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("school not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentSchool_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
 	        .delete(SCHOOL_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentSchool_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var schoolEntity = createSchool("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", schoolEntity.getId())
 	        .delete(SCHOOL_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findSchoolEntityByPK(
	 schoolEntity.getId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentSchoolWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var schoolEntity = createSchool("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", schoolEntity.getId())
        	.delete(SCHOOL_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentSchool_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var schoolEntity = createSchool("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("id", schoolEntity.getId())
			.get(SCHOOL_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"school.id",
				 Matchers.notNullValue(),
				"school.id",
				 is(schoolEntity.getId()),
				"school.name",
				 Matchers.notNullValue(),
				"school.name",
				 is(schoolEntity.getName())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentSchool_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var schoolEntity = createSchool("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("id", "id")
			.get(SCHOOL_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentSchoolWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var schoolEntity = createSchool("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("id", schoolEntity.getId())
			.get(SCHOOL_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"school.id",
				 Matchers.notNullValue(),
				"school.id",
				 is(schoolEntity.getId()),
				"school.name",
				 Matchers.notNullValue(),
				"school.name",
				 is(schoolEntity.getName())
				);
	}
   
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset0AndLimit5_shouldReturnListOf5School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"school[0]",
					 hasKey("id"),
					"school[0]",
					 hasKey("name") ,
					"schoolCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset0AndLimit10_shouldReturnListOf10School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"school[0]",
					 hasKey("id"),
					"school[0]",
					 hasKey("name") ,
					"schoolCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset0AndLimit15_shouldReturnListOf10School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"school[0]",
					 hasKey("id"),
					"school[0]",
					 hasKey("name") ,
					"schoolCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset1AndLimit5_shouldReturnListOf5School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 5)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"school[0]",
					 hasKey("id"),
					"school[0]",
					 hasKey("name") ,
					"schoolCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset2AndLimit5_shouldReturnListOf0School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 2)
	        .queryParam("limit", 5)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"schoolCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset1AndLimit10_shouldReturnListOf0School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 10)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"schoolCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilterWithOffset1AndLimit115_shouldReturnListOf0School() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSchool(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 1)
	        .queryParam("limit", 15)
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"schoolCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							""+"||"+"name|eq|"+schoolEntity.getName()+
							""

	        )  
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 is(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 is(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							""+"||"+"name|eq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 is(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 is(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|neq|"+schoolEntity.getId()+
							""+"||"+"name|neq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 not(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 not(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|like|"+schoolEntity.getId()+
							""+"||"+"name|like|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 is(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 is(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|nlike|"+schoolEntity.getId()+
							""+"||"+"name|nlike|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 not(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 not(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							""+"||"+"name|eq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 is(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 is(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredSchool() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							""+"||"+"name|eq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-id,-name"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"school[0].id",
				 Matchers.notNullValue(),
				"school[0].id",
				 is(schoolEntity.getId()),
				"school[0].name",
				 Matchers.notNullValue(),
				"school[0].name",
				 is(schoolEntity.getName())
				);
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "idXXX|eq|"+schoolEntity.getId()+
							""+"||"+"nameXXX|eq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eqXXX|"+schoolEntity.getId()+
							""+"||"+"name|eqXXX|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							""+"||"+"name|eq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							""+"||"+"name|eq|"+schoolEntity.getName()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","idXXX,nameXXX"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10School_whenExecuteFindSchoolByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var schoolEntity = createSchool("");
	    for (int i = 0; i < 9; i++) {
	    	createSchool(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "id|eq|"+schoolEntity.getId()+
							";:')"+"||"+"name|eq|"+schoolEntity.getName()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","id,name"
	        )
	        .get(SCHOOL_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
