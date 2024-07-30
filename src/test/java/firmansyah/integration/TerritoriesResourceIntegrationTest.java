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
import firmansyah.application.web.model.request.NewTerritoriesRequest;
import firmansyah.application.web.model.request.UpdateTerritoriesRequest;
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
public class TerritoriesResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String TERRITORIES_RESOURCE_PATH = API_PREFIX + "/firmansyah/territories";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewTerritories_whenExecuteCreateEndpoint_shouldReturnCreatedTerritories201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewTerritoriesRequest territories = new NewTerritoriesRequest();
			territories.setTerritoryId("territoryId");
			territories.setTerritoryDescription("territoryDescription");
			final var regionRegionIdEntity= createRegion("");
			territories.setRegionId( regionRegionIdEntity.getRegionId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(territories)).when()
		       .post(TERRITORIES_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"territories.territoryId",
					 Matchers.notNullValue(),
					"territories.territoryId",
					 is(territories.getTerritoryId()),
					"territories.territoryDescription",
					 Matchers.notNullValue(),
					"territories.territoryDescription",
					 is(territories.getTerritoryDescription()),
					"territories.regionRegionIdResponse.regionId",
					 Matchers.notNullValue(),
					"territories.regionRegionIdResponse.regionId",
					 is( regionRegionIdEntity.getRegionId())
					);
  	}
  
  	@Test
  	public void givenANewTerritoriesWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewTerritoriesRequest territories = new NewTerritoriesRequest();
			territories.setTerritoryId("territoryId");
			territories.setTerritoryDescription("territoryDescription");
			final var regionRegionIdEntity= createRegion("");
			territories.setRegionId( regionRegionIdEntity.getRegionId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(territories))
        	.post(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidTerritories_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewTerritoriesRequest territories = new NewTerritoriesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(territories))
        	.when()
        	.post(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3),
            	"errors.body",
            	hasItems(
						"territoryId of Territories must not be blank",
						"territoryDescription of Territories must not be blank",
						"regionId of Territories must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentTerritories_whenExecuteCreateEndpoint_shouldReturnConflictTerritoriesRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var territoriesEntity = createTerritories("");
		NewTerritoriesRequest territories = new NewTerritoriesRequest();
			territories.setTerritoryId(territoriesEntity.getTerritoryId());
			territories.setTerritoryDescription("territoryDescription");
			final var regionRegionIdEntity= createRegion("");
			territories.setRegionId( regionRegionIdEntity.getRegionId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(territories))
        	.post(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("territories already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentTerritories_whenExecuteUpdateEndpoint_shouldReturnUpdatedTerritories()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var territoriesEntity = createTerritories("");
		UpdateTerritoriesRequest territories = new UpdateTerritoriesRequest();
			territories.setTerritoryId(territoriesEntity.getTerritoryId());
			territories.setTerritoryDescription("territoryDescription");
			final var regionRegionIdEntity= createRegion("");
			territories.setRegionId( regionRegionIdEntity.getRegionId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(territories))
        	.put(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"territories.territoryId",
				 Matchers.notNullValue(),
				"territories.territoryId",
				 is(territories.getTerritoryId()),
				"territories.territoryDescription",
				 Matchers.notNullValue(),
				"territories.territoryDescription",
				 is(territories.getTerritoryDescription()),
				"territories.regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories.regionRegionIdResponse.regionId",
				 is(regionRegionIdEntity.getRegionId())
        		);
        	
			Assertions.assertEquals(territories.getTerritoryId(),
			 territoriesEntity.getTerritoryId());Assertions.assertEquals(territories.getTerritoryDescription(),
			 territoriesEntity.getTerritoryDescription());Assertions.assertEquals(territories.getRegionId(),
			 regionRegionIdEntity.getRegionId());
  	}
  
  	@Test
  	public void givenAExistentTerritoriesWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var territoriesEntity = createTerritories("");
		UpdateTerritoriesRequest territories = new UpdateTerritoriesRequest();
			territories.setTerritoryId(territoriesEntity.getTerritoryId());
			territories.setTerritoryDescription("territoryDescription");
			final var regionRegionIdEntity= createRegion("");
			territories.setRegionId( regionRegionIdEntity.getRegionId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(territories))
        	.put(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentTerritories_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateTerritoriesRequest territories = new UpdateTerritoriesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(territories))
        	.when()
        	.put(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"territoryId of Territories must not be blank",
						"territoryDescription of Territories must not be blank",
						"regionId of Territories must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentTerritories_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateTerritoriesRequest territories = new UpdateTerritoriesRequest();
			territories.setTerritoryId("territoryId");
			territories.setTerritoryDescription("territoryDescription");
			final var regionRegionIdEntity= createRegion("");
			territories.setRegionId( regionRegionIdEntity.getRegionId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(territories))
        	.when()
        	.put(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("territories not found"));
  	}
  
  	@Test
  	public void givenANewTerritoriesWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewTerritoriesRequest territories = new NewTerritoriesRequest();
			territories.setTerritoryId("territoryId");
			territories.setTerritoryDescription("territoryDescription");
			territories.setRegionId(2);
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(territories))
        	.when()
        	.post(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("region not found")));
  	}
  
   	@Test
   	public void givenAExistentTerritoriesWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var territoriesEntity = createTerritories("");
		UpdateTerritoriesRequest territories = new UpdateTerritoriesRequest();
			territories.setTerritoryId(territoriesEntity.getTerritoryId());
			territories.setTerritoryDescription("territoryDescription");
			territories.setRegionId(2);
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(territories))
         	.when()
         	.put(TERRITORIES_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("territories not found"),hasItems("region not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentTerritories_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("territoryId", "territoryId")
 	        .delete(TERRITORIES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentTerritories_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var territoriesEntity = createTerritories("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("territoryId", territoriesEntity.getTerritoryId())
 	        .delete(TERRITORIES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findTerritoriesEntityByPK(
	 territoriesEntity.getTerritoryId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentTerritoriesWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var territoriesEntity = createTerritories("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("territoryId", territoriesEntity.getTerritoryId())
        	.delete(TERRITORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentTerritories_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var territoriesEntity = createTerritories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("territoryId", territoriesEntity.getTerritoryId())
			.get(TERRITORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"territories.territoryId",
				 Matchers.notNullValue(),
				"territories.territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories.territoryDescription",
				 Matchers.notNullValue(),
				"territories.territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories.regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories.regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentTerritories_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var territoriesEntity = createTerritories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("territoryId", "territoryId")
			.get(TERRITORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentTerritoriesWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var territoriesEntity = createTerritories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("territoryId", territoriesEntity.getTerritoryId())
			.get(TERRITORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"territories.territoryId",
				 Matchers.notNullValue(),
				"territories.territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories.territoryDescription",
				 Matchers.notNullValue(),
				"territories.territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories.regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories.regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
	}
   
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilterWithOffset0AndLimit5_shouldReturnListOf5Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"territories[0]",
					 hasKey("territoryId"),
					"territories[0]",
					 hasKey("territoryDescription"),
					"territories[0]",
					 hasKey("regionRegionIdResponse"),
					"territoriesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilterWithOffset0AndLimit10_shouldReturnListOf10Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"territories[0]",
					 hasKey("territoryId"),
					"territories[0]",
					 hasKey("territoryDescription"),
					"territories[0]",
					 hasKey("regionRegionIdResponse"),
					"territoriesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilterWithOffset0AndLimit15_shouldReturnListOf10Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"territories[0]",
					 hasKey("territoryId"),
					"territories[0]",
					 hasKey("territoryDescription"),
					"territories[0]",
					 hasKey("regionRegionIdResponse"),
					"territoriesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Territories_whenExecuteFindTerritoriesByFilterWithOffset10AndLimit5_shouldReturnListOf5Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"territories[0]",
					 hasKey("territoryId"),
					"territories[0]",
					 hasKey("territoryDescription"),
					"territories[0]",
					 hasKey("regionRegionIdResponse"),
					"territoriesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilterWithOffset20AndLimit5_shouldReturnListOf0Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"territoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilterWithOffset10AndLimit10_shouldReturnListOf0Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"territoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilterWithOffset10AndLimit115_shouldReturnListOf0Territories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createTerritories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"territoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|neq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|neq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|neq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 not(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 not(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 not(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|like|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|like|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|like|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|nlike|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|nlike|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|nlike|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 not(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 not(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 not(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","territoryId,territoryDescription,regionId"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredTerritories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","-territoryId,-territoryDescription,-regionId"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"territories[0].territoryId",
				 Matchers.notNullValue(),
				"territories[0].territoryId",
				 is(territoriesEntity.getTerritoryId()),
				"territories[0].territoryDescription",
				 Matchers.notNullValue(),
				"territories[0].territoryDescription",
				 is(territoriesEntity.getTerritoryDescription()),
				"territories[0].regionRegionIdResponse.regionId",
				 Matchers.notNullValue(),
				"territories[0].regionRegionIdResponse.regionId",
				 is(territoriesEntity.getRegionRegionId().getRegionId())
				);
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryIdXXX|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescriptionXXX|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionIdXXX|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","territoryId,territoryDescription,regionId"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eqXXX|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eqXXX|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eqXXX|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","territoryId,territoryDescription,regionId"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","territoryId,territoryDescription,regionId"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							""+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							""+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","territoryIdXXX,territoryDescriptionXXX,regionIdXXX"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Territories_whenExecuteFindTerritoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var territoriesEntity = createTerritories("");
	    for (int i = 0; i < 9; i++) {
	    	createTerritories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "territoryId|eq|"+territoriesEntity.getTerritoryId()+
							";:')"+"||"+"territoryDescription|eq|"+territoriesEntity.getTerritoryDescription()+
							";:')"+"||"+"regionId|eq|"+territoriesEntity.getRegionRegionId().getRegionId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR"
	        )
	        .queryParam("sort","territoryId,territoryDescription,regionId"
	        )
	        .get(TERRITORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
