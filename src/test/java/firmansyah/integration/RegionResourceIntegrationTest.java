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
import firmansyah.application.web.model.request.NewRegionRequest;
import firmansyah.application.web.model.request.UpdateRegionRequest;
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
public class RegionResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String REGION_RESOURCE_PATH = API_PREFIX + "/firmansyah/region";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewRegion_whenExecuteCreateEndpoint_shouldReturnCreatedRegion201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewRegionRequest region = new NewRegionRequest();
			region.setRegionId(2);
			region.setRegionDescription("regionDescription");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(region)).when()
		       .post(REGION_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"region.regionId",
					 Matchers.notNullValue(),
					"region.regionId",
					 is(region.getRegionId()),
					"region.regionDescription",
					 Matchers.notNullValue(),
					"region.regionDescription",
					 is(region.getRegionDescription())
					);
  	}
  
  	@Test
  	public void givenANewRegionWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewRegionRequest region = new NewRegionRequest();
			region.setRegionId(2);
			region.setRegionDescription("regionDescription");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(region))
        	.post(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidRegion_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewRegionRequest region = new NewRegionRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(region))
        	.when()
        	.post(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"regionId of Region must not be blank",
						"regionDescription of Region must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentRegion_whenExecuteCreateEndpoint_shouldReturnConflictRegionRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var regionEntity = createRegion("");
		NewRegionRequest region = new NewRegionRequest();
			region.setRegionId(regionEntity.getRegionId());
			region.setRegionDescription("regionDescription");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(region))
        	.post(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("region already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentRegion_whenExecuteUpdateEndpoint_shouldReturnUpdatedRegion()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var regionEntity = createRegion("");
		UpdateRegionRequest region = new UpdateRegionRequest();
			region.setRegionId(regionEntity.getRegionId());
			region.setRegionDescription("regionDescription");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(region))
        	.put(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"region.regionId",
				 Matchers.notNullValue(),
				"region.regionId",
				 is(region.getRegionId()),
				"region.regionDescription",
				 Matchers.notNullValue(),
				"region.regionDescription",
				 is(region.getRegionDescription())
        		);
        	
			Assertions.assertEquals(region.getRegionId(),
			 regionEntity.getRegionId());Assertions.assertEquals(region.getRegionDescription(),
			 regionEntity.getRegionDescription());
  	}
  
  	@Test
  	public void givenAExistentRegionWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var regionEntity = createRegion("");
		UpdateRegionRequest region = new UpdateRegionRequest();
			region.setRegionId(regionEntity.getRegionId());
			region.setRegionDescription("regionDescription");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(region))
        	.put(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentRegion_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateRegionRequest region = new UpdateRegionRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(region))
        	.when()
        	.put(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"regionId of Region must not be blank",
						"regionDescription of Region must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentRegion_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateRegionRequest region = new UpdateRegionRequest();
			region.setRegionId(2);
			region.setRegionDescription("regionDescription");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(region))
        	.when()
        	.put(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("region not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentRegion_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("regionId", 2)
 	        .delete(REGION_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentRegion_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var regionEntity = createRegion("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("regionId", regionEntity.getRegionId())
 	        .delete(REGION_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findRegionEntityByPK(
	 regionEntity.getRegionId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentRegionWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var regionEntity = createRegion("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("regionId", regionEntity.getRegionId())
        	.delete(REGION_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentRegion_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var regionEntity = createRegion("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("regionId", regionEntity.getRegionId())
			.get(REGION_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"region.regionId",
				 Matchers.notNullValue(),
				"region.regionId",
				 is(regionEntity.getRegionId()),
				"region.regionDescription",
				 Matchers.notNullValue(),
				"region.regionDescription",
				 is(regionEntity.getRegionDescription())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentRegion_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var regionEntity = createRegion("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("regionId", 2)
			.get(REGION_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentRegionWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var regionEntity = createRegion("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("regionId", regionEntity.getRegionId())
			.get(REGION_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"region.regionId",
				 Matchers.notNullValue(),
				"region.regionId",
				 is(regionEntity.getRegionId()),
				"region.regionDescription",
				 Matchers.notNullValue(),
				"region.regionDescription",
				 is(regionEntity.getRegionDescription())
				);
	}
   
	@Test
	public void given10Region_whenExecuteFindRegionByFilterWithOffset0AndLimit5_shouldReturnListOf5Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"region[0]",
					 hasKey("regionId"),
					"region[0]",
					 hasKey("regionDescription") ,
					"regionCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilterWithOffset0AndLimit10_shouldReturnListOf10Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"region[0]",
					 hasKey("regionId"),
					"region[0]",
					 hasKey("regionDescription") ,
					"regionCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilterWithOffset0AndLimit15_shouldReturnListOf10Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"region[0]",
					 hasKey("regionId"),
					"region[0]",
					 hasKey("regionDescription") ,
					"regionCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Region_whenExecuteFindRegionByFilterWithOffset10AndLimit5_shouldReturnListOf5Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"region[0]",
					 hasKey("regionId"),
					"region[0]",
					 hasKey("regionDescription") ,
					"regionCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilterWithOffset20AndLimit5_shouldReturnListOf0Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"regionCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilterWithOffset10AndLimit10_shouldReturnListOf0Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"regionCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilterWithOffset10AndLimit115_shouldReturnListOf0Region() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createRegion(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"regionCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 is(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 is(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 is(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 is(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|neq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|neq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 not(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 not(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|like|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|like|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 is(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 is(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|nlike|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|nlike|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 not(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 not(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","regionId,regionDescription"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 is(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 is(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredRegion() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-regionId,-regionDescription"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"region[0].regionId",
				 Matchers.notNullValue(),
				"region[0].regionId",
				 is(regionEntity.getRegionId()),
				"region[0].regionDescription",
				 Matchers.notNullValue(),
				"region[0].regionDescription",
				 is(regionEntity.getRegionDescription())
				);
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionIdXXX|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescriptionXXX|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","regionId,regionDescription"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eqXXX|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eqXXX|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","regionId,regionDescription"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","regionId,regionDescription"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							""+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","regionIdXXX,regionDescriptionXXX"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Region_whenExecuteFindRegionByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var regionEntity = createRegion("");
	    for (int i = 0; i < 9; i++) {
	    	createRegion(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "regionId|eq|"+regionEntity.getRegionId()+
							";:')"+"||"+"regionDescription|eq|"+regionEntity.getRegionDescription()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","regionId,regionDescription"
	        )
	        .get(REGION_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
