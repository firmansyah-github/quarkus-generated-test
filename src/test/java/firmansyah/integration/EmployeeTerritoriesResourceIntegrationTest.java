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
import firmansyah.application.web.model.request.NewEmployeeTerritoriesRequest;
import firmansyah.application.web.model.request.UpdateEmployeeTerritoriesRequest;
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
public class EmployeeTerritoriesResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String EMPLOYEETERRITORIES_RESOURCE_PATH = API_PREFIX + "/firmansyah/employeeTerritories";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewEmployeeTerritories_whenExecuteCreateEndpoint_shouldReturnCreatedEmployeeTerritories201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewEmployeeTerritoriesRequest employeeTerritories = new NewEmployeeTerritoriesRequest();
			final var employeesEmployeeIdEntity= createEmployees("");
			employeeTerritories.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			final var territoriesTerritoryIdEntity= createTerritories("");
			employeeTerritories.setTerritoryId( territoriesTerritoryIdEntity.getTerritoryId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(employeeTerritories)).when()
		       .post(EMPLOYEETERRITORIES_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"employeeTerritories.employeesEmployeeIdResponse.employeeId",
					 Matchers.notNullValue(),
					"employeeTerritories.employeesEmployeeIdResponse.employeeId",
					 is( employeesEmployeeIdEntity.getEmployeeId()),
					"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
					 Matchers.notNullValue(),
					"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
					 is( territoriesTerritoryIdEntity.getTerritoryId())
					);
  	}
  
  	@Test
  	public void givenANewEmployeeTerritoriesWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewEmployeeTerritoriesRequest employeeTerritories = new NewEmployeeTerritoriesRequest();
			final var employeesEmployeeIdEntity= createEmployees("");
			employeeTerritories.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			final var territoriesTerritoryIdEntity= createTerritories("");
			employeeTerritories.setTerritoryId( territoriesTerritoryIdEntity.getTerritoryId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.post(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidEmployeeTerritories_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewEmployeeTerritoriesRequest employeeTerritories = new NewEmployeeTerritoriesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.when()
        	.post(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"employeeId of EmployeeTerritories must not be blank",
						"territoryId of EmployeeTerritories must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentEmployeeTerritories_whenExecuteCreateEndpoint_shouldReturnConflictEmployeeTerritoriesRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var employeeTerritoriesEntity = createEmployeeTerritories("");
		NewEmployeeTerritoriesRequest employeeTerritories = new NewEmployeeTerritoriesRequest();
			employeeTerritories.setEmployeeId(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId());
			employeeTerritories.setTerritoryId(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.post(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("employeeterritories already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentEmployeeTerritories_whenExecuteUpdateEndpoint_shouldReturnUpdatedEmployeeTerritories()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var employeeTerritoriesEntity = createEmployeeTerritories("");
		UpdateEmployeeTerritoriesRequest employeeTerritories = new UpdateEmployeeTerritoriesRequest();
			employeeTerritories.setEmployeeId(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId());
			employeeTerritories.setTerritoryId(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.put(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"employeeTerritories.employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories.employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
        		);
        	
			Assertions.assertEquals(employeeTerritories.getEmployeeId(),
			 employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId());Assertions.assertEquals(employeeTerritories.getTerritoryId(),
			 employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId());
  	}
  
  	@Test
  	public void givenAExistentEmployeeTerritoriesWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var employeeTerritoriesEntity = createEmployeeTerritories("");
		UpdateEmployeeTerritoriesRequest employeeTerritories = new UpdateEmployeeTerritoriesRequest();
			employeeTerritories.setEmployeeId(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId());
			employeeTerritories.setTerritoryId(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.put(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentEmployeeTerritories_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateEmployeeTerritoriesRequest employeeTerritories = new UpdateEmployeeTerritoriesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.when()
        	.put(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"employeeId of EmployeeTerritories must not be blank",
						"territoryId of EmployeeTerritories must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentEmployeeTerritories_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateEmployeeTerritoriesRequest employeeTerritories = new UpdateEmployeeTerritoriesRequest();
			final var employeesEmployeeIdEntity= createEmployees("");
			employeeTerritories.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			final var territoriesTerritoryIdEntity= createTerritories("");
			employeeTerritories.setTerritoryId( territoriesTerritoryIdEntity.getTerritoryId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.when()
        	.put(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("employeeTerritories not found"));
  	}
  
  	@Test
  	public void givenANewEmployeeTerritoriesWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewEmployeeTerritoriesRequest employeeTerritories = new NewEmployeeTerritoriesRequest();
			employeeTerritories.setEmployeeId(2);
			employeeTerritories.setTerritoryId("territoryId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employeeTerritories))
        	.when()
        	.post(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("employees not found"),hasItems("territories not found")));
  	}
  
   	@Test
   	public void givenAExistentEmployeeTerritoriesWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var employeeTerritoriesEntity = createEmployeeTerritories("");
		UpdateEmployeeTerritoriesRequest employeeTerritories = new UpdateEmployeeTerritoriesRequest();
			employeeTerritories.setEmployeeId(2);
			employeeTerritories.setTerritoryId("territoryId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(employeeTerritories))
         	.when()
         	.put(EMPLOYEETERRITORIES_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("employeeTerritories not found"),hasItems("employees not found"),hasItems("territories not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentEmployeeTerritories_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("employeeId", 2)
			.queryParam("territoryId", "territoryId")
 	        .delete(EMPLOYEETERRITORIES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentEmployeeTerritories_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var employeeTerritoriesEntity = createEmployeeTerritories("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("employeeId", employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId())
		.queryParam("territoryId", employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId())
 	        .delete(EMPLOYEETERRITORIES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findEmployeeTerritoriesEntityByPK(
		 employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()
		,		     
		 employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentEmployeeTerritoriesWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var employeeTerritoriesEntity = createEmployeeTerritories("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		.queryParam("employeeId", employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId())
		.queryParam("territoryId", employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId())
        	.delete(EMPLOYEETERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentEmployeeTerritories_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var employeeTerritoriesEntity = createEmployeeTerritories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("employeeId", employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId())
		.queryParam("territoryId", employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId())
			.get(EMPLOYEETERRITORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"employeeTerritories.employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories.employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentEmployeeTerritories_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var employeeTerritoriesEntity = createEmployeeTerritories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("employeeId", 2)
			.queryParam("territoryId", "territoryId")
			.get(EMPLOYEETERRITORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentEmployeeTerritoriesWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var employeeTerritoriesEntity = createEmployeeTerritories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		.queryParam("employeeId", employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId())
		.queryParam("territoryId", employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId())
			.get(EMPLOYEETERRITORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"employeeTerritories.employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories.employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories.territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
	}
   
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset0AndLimit5_shouldReturnListOf5EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employeeTerritories[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"employeeTerritories[0]",
					 hasKey("territoriesTerritoryIdResponse"),
					"employeeTerritoriesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset0AndLimit10_shouldReturnListOf10EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employeeTerritories[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"employeeTerritories[0]",
					 hasKey("territoriesTerritoryIdResponse"),
					"employeeTerritoriesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset0AndLimit15_shouldReturnListOf10EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employeeTerritories[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"employeeTerritories[0]",
					 hasKey("territoriesTerritoryIdResponse"),
					"employeeTerritoriesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset10AndLimit5_shouldReturnListOf5EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employeeTerritories[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"employeeTerritories[0]",
					 hasKey("territoriesTerritoryIdResponse"),
					"employeeTerritoriesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset20AndLimit5_shouldReturnListOf0EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"employeeTerritoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset10AndLimit10_shouldReturnListOf0EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"employeeTerritoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilterWithOffset10AndLimit115_shouldReturnListOf0EmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"employeeTerritoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|neq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|neq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 not(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 not(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|like|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|like|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|nlike|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|nlike|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 not(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 not(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","employeeId,territoryId"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredEmployeeTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-employeeId,-territoryId"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].employeesEmployeeIdResponse.employeeId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getEmployeesEmployeeId().getEmployeeId()),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 Matchers.notNullValue(),
				"employeeTerritories[0].territoriesTerritoryIdResponse.territoryId",
				 is(employeeTerritoriesEntity.getPrimaryKey().getTerritoriesTerritoryId().getTerritoryId())
				);
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeIdXXX|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryIdXXX|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","employeeId,territoryId"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eqXXX|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eqXXX|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","employeeId,territoryId"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","employeeId,territoryId"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","employeeIdXXX,territoryIdXXX"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10EmployeeTerritories_whenExecuteFindEmployeeTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeeTerritoriesEntity = createEmployeeTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployeeTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeeTerritoriesEntity.getEmployeesEmployeeId().getEmployeeId()+
							";:')"+"||"+"territoryId|eq|"+employeeTerritoriesEntity.getTerritoriesTerritoryId().getTerritoryId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","employeeId,territoryId"
	        )
	        .get(EMPLOYEETERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
