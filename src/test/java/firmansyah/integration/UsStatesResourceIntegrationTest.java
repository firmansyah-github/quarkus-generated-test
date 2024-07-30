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
import firmansyah.application.web.model.request.NewUsStatesRequest;
import firmansyah.application.web.model.request.UpdateUsStatesRequest;
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
public class UsStatesResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String USSTATES_RESOURCE_PATH = API_PREFIX + "/firmansyah/usStates";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewUsStates_whenExecuteCreateEndpoint_shouldReturnCreatedUsStates201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewUsStatesRequest usStates = new NewUsStatesRequest();
			usStates.setStateId(2);
			usStates.setStateName("stateName");
			usStates.setStateAbbr("stateAbbr");
			usStates.setStateRegion("stateRegion");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(usStates)).when()
		       .post(USSTATES_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"usStates.stateId",
					 Matchers.notNullValue(),
					"usStates.stateId",
					 is(usStates.getStateId()),
					"usStates.stateName",
					 Matchers.notNullValue(),
					"usStates.stateName",
					 is(usStates.getStateName()),
					"usStates.stateAbbr",
					 Matchers.notNullValue(),
					"usStates.stateAbbr",
					 is(usStates.getStateAbbr()),
					"usStates.stateRegion",
					 Matchers.notNullValue(),
					"usStates.stateRegion",
					 is(usStates.getStateRegion())
					);
  	}
  
  	@Test
  	public void givenANewUsStatesWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewUsStatesRequest usStates = new NewUsStatesRequest();
			usStates.setStateId(2);
			usStates.setStateName("stateName");
			usStates.setStateAbbr("stateAbbr");
			usStates.setStateRegion("stateRegion");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(usStates))
        	.post(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidUsStates_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewUsStatesRequest usStates = new NewUsStatesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(usStates))
        	.when()
        	.post(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1),
            	"errors.body",
            	hasItems(
						"stateId of UsStates must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentUsStates_whenExecuteCreateEndpoint_shouldReturnConflictUsStatesRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var usStatesEntity = createUsStates("");
		NewUsStatesRequest usStates = new NewUsStatesRequest();
			usStates.setStateId(usStatesEntity.getStateId());
			usStates.setStateName("stateName");
			usStates.setStateAbbr("stateAbbr");
			usStates.setStateRegion("stateRegion");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(usStates))
        	.post(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("usstates already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentUsStates_whenExecuteUpdateEndpoint_shouldReturnUpdatedUsStates()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var usStatesEntity = createUsStates("");
		UpdateUsStatesRequest usStates = new UpdateUsStatesRequest();
			usStates.setStateId(usStatesEntity.getStateId());
			usStates.setStateName("stateName");
			usStates.setStateAbbr("stateAbbr");
			usStates.setStateRegion("stateRegion");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(usStates))
        	.put(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"usStates.stateId",
				 Matchers.notNullValue(),
				"usStates.stateId",
				 is(usStates.getStateId()),
				"usStates.stateName",
				 Matchers.notNullValue(),
				"usStates.stateName",
				 is(usStates.getStateName()),
				"usStates.stateAbbr",
				 Matchers.notNullValue(),
				"usStates.stateAbbr",
				 is(usStates.getStateAbbr()),
				"usStates.stateRegion",
				 Matchers.notNullValue(),
				"usStates.stateRegion",
				 is(usStates.getStateRegion())
        		);
        	
			Assertions.assertEquals(usStates.getStateId(),
			 usStatesEntity.getStateId());Assertions.assertEquals(usStates.getStateName(),
			 usStatesEntity.getStateName());Assertions.assertEquals(usStates.getStateAbbr(),
			 usStatesEntity.getStateAbbr());Assertions.assertEquals(usStates.getStateRegion(),
			 usStatesEntity.getStateRegion());
  	}
  
  	@Test
  	public void givenAExistentUsStatesWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var usStatesEntity = createUsStates("");
		UpdateUsStatesRequest usStates = new UpdateUsStatesRequest();
			usStates.setStateId(usStatesEntity.getStateId());
			usStates.setStateName("stateName");
			usStates.setStateAbbr("stateAbbr");
			usStates.setStateRegion("stateRegion");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(usStates))
        	.put(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentUsStates_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateUsStatesRequest usStates = new UpdateUsStatesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(usStates))
        	.when()
        	.put(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"stateId of UsStates must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentUsStates_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateUsStatesRequest usStates = new UpdateUsStatesRequest();
			usStates.setStateId(2);
			usStates.setStateName("stateName");
			usStates.setStateAbbr("stateAbbr");
			usStates.setStateRegion("stateRegion");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(usStates))
        	.when()
        	.put(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("usStates not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentUsStates_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("stateId", 2)
 	        .delete(USSTATES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentUsStates_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var usStatesEntity = createUsStates("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("stateId", usStatesEntity.getStateId())
 	        .delete(USSTATES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findUsStatesEntityByPK(
	 usStatesEntity.getStateId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentUsStatesWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var usStatesEntity = createUsStates("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("stateId", usStatesEntity.getStateId())
        	.delete(USSTATES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentUsStates_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var usStatesEntity = createUsStates("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("stateId", usStatesEntity.getStateId())
			.get(USSTATES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"usStates.stateId",
				 Matchers.notNullValue(),
				"usStates.stateId",
				 is(usStatesEntity.getStateId()),
				"usStates.stateName",
				 Matchers.notNullValue(),
				"usStates.stateName",
				 is(usStatesEntity.getStateName()),
				"usStates.stateAbbr",
				 Matchers.notNullValue(),
				"usStates.stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates.stateRegion",
				 Matchers.notNullValue(),
				"usStates.stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentUsStates_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var usStatesEntity = createUsStates("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("stateId", 2)
			.get(USSTATES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentUsStatesWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var usStatesEntity = createUsStates("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("stateId", usStatesEntity.getStateId())
			.get(USSTATES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"usStates.stateId",
				 Matchers.notNullValue(),
				"usStates.stateId",
				 is(usStatesEntity.getStateId()),
				"usStates.stateName",
				 Matchers.notNullValue(),
				"usStates.stateName",
				 is(usStatesEntity.getStateName()),
				"usStates.stateAbbr",
				 Matchers.notNullValue(),
				"usStates.stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates.stateRegion",
				 Matchers.notNullValue(),
				"usStates.stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
	}
   
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilterWithOffset0AndLimit5_shouldReturnListOf5UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"usStates[0]",
					 hasKey("stateId"),
					"usStates[0]",
					 hasKey("stateName"),
					"usStates[0]",
					 hasKey("stateAbbr"),
					"usStates[0]",
					 hasKey("stateRegion") ,
					"usStatesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilterWithOffset0AndLimit10_shouldReturnListOf10UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"usStates[0]",
					 hasKey("stateId"),
					"usStates[0]",
					 hasKey("stateName"),
					"usStates[0]",
					 hasKey("stateAbbr"),
					"usStates[0]",
					 hasKey("stateRegion") ,
					"usStatesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilterWithOffset0AndLimit15_shouldReturnListOf10UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"usStates[0]",
					 hasKey("stateId"),
					"usStates[0]",
					 hasKey("stateName"),
					"usStates[0]",
					 hasKey("stateAbbr"),
					"usStates[0]",
					 hasKey("stateRegion") ,
					"usStatesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15UsStates_whenExecuteFindUsStatesByFilterWithOffset10AndLimit5_shouldReturnListOf5UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"usStates[0]",
					 hasKey("stateId"),
					"usStates[0]",
					 hasKey("stateName"),
					"usStates[0]",
					 hasKey("stateAbbr"),
					"usStates[0]",
					 hasKey("stateRegion") ,
					"usStatesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilterWithOffset20AndLimit5_shouldReturnListOf0UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"usStatesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilterWithOffset10AndLimit10_shouldReturnListOf0UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"usStatesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilterWithOffset10AndLimit115_shouldReturnListOf0UsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createUsStates(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"usStatesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 is(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 is(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 is(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 is(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|neq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|neq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|neq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|neq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 not(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 not(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 not(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 not(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|like|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|like|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|like|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|like|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 is(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 is(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|nlike|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|nlike|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|nlike|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|nlike|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 not(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 not(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 not(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 not(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","stateId,stateName,stateAbbr,stateRegion"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 is(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 is(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredUsStates() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","-stateId,-stateName,-stateAbbr,-stateRegion"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"usStates[0].stateId",
				 Matchers.notNullValue(),
				"usStates[0].stateId",
				 is(usStatesEntity.getStateId()),
				"usStates[0].stateName",
				 Matchers.notNullValue(),
				"usStates[0].stateName",
				 is(usStatesEntity.getStateName()),
				"usStates[0].stateAbbr",
				 Matchers.notNullValue(),
				"usStates[0].stateAbbr",
				 is(usStatesEntity.getStateAbbr()),
				"usStates[0].stateRegion",
				 Matchers.notNullValue(),
				"usStates[0].stateRegion",
				 is(usStatesEntity.getStateRegion())
				);
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateIdXXX|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateNameXXX|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbrXXX|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegionXXX|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","stateId,stateName,stateAbbr,stateRegion"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eqXXX|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eqXXX|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eqXXX|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eqXXX|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","stateId,stateName,stateAbbr,stateRegion"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","stateId,stateName,stateAbbr,stateRegion"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							""+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							""+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							""+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","stateIdXXX,stateNameXXX,stateAbbrXXX,stateRegionXXX"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10UsStates_whenExecuteFindUsStatesByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var usStatesEntity = createUsStates("");
	    for (int i = 0; i < 9; i++) {
	    	createUsStates(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "stateId|eq|"+usStatesEntity.getStateId()+
							";:')"+"||"+"stateName|eq|"+usStatesEntity.getStateName()+
							";:')"+"||"+"stateAbbr|eq|"+usStatesEntity.getStateAbbr()+
							";:')"+"||"+"stateRegion|eq|"+usStatesEntity.getStateRegion()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","stateId,stateName,stateAbbr,stateRegion"
	        )
	        .get(USSTATES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
