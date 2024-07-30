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
import firmansyah.application.web.model.request.NewCustomerDemographicsRequest;
import firmansyah.application.web.model.request.UpdateCustomerDemographicsRequest;
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
public class CustomerDemographicsResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String CUSTOMERDEMOGRAPHICS_RESOURCE_PATH = API_PREFIX + "/firmansyah/customerDemographics";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewCustomerDemographics_whenExecuteCreateEndpoint_shouldReturnCreatedCustomerDemographics201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewCustomerDemographicsRequest customerDemographics = new NewCustomerDemographicsRequest();
			customerDemographics.setCustomerTypeId("customerTypeId");
			customerDemographics.setCustomerDesc("customerDesc");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(customerDemographics)).when()
		       .post(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"customerDemographics.customerTypeId",
					 Matchers.notNullValue(),
					"customerDemographics.customerTypeId",
					 is(customerDemographics.getCustomerTypeId()),
					"customerDemographics.customerDesc",
					 Matchers.notNullValue(),
					"customerDemographics.customerDesc",
					 is(customerDemographics.getCustomerDesc())
					);
  	}
  
  	@Test
  	public void givenANewCustomerDemographicsWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewCustomerDemographicsRequest customerDemographics = new NewCustomerDemographicsRequest();
			customerDemographics.setCustomerTypeId("customerTypeId");
			customerDemographics.setCustomerDesc("customerDesc");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.post(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidCustomerDemographics_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewCustomerDemographicsRequest customerDemographics = new NewCustomerDemographicsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.when()
        	.post(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1),
            	"errors.body",
            	hasItems(
						"customerTypeId of CustomerDemographics must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentCustomerDemographics_whenExecuteCreateEndpoint_shouldReturnConflictCustomerDemographicsRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var customerDemographicsEntity = createCustomerDemographics("");
		NewCustomerDemographicsRequest customerDemographics = new NewCustomerDemographicsRequest();
			customerDemographics.setCustomerTypeId(customerDemographicsEntity.getCustomerTypeId());
			customerDemographics.setCustomerDesc("customerDesc");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.post(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("customerdemographics already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentCustomerDemographics_whenExecuteUpdateEndpoint_shouldReturnUpdatedCustomerDemographics()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var customerDemographicsEntity = createCustomerDemographics("");
		UpdateCustomerDemographicsRequest customerDemographics = new UpdateCustomerDemographicsRequest();
			customerDemographics.setCustomerTypeId(customerDemographicsEntity.getCustomerTypeId());
			customerDemographics.setCustomerDesc("customerDesc");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.put(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"customerDemographics.customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics.customerTypeId",
				 is(customerDemographics.getCustomerTypeId()),
				"customerDemographics.customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics.customerDesc",
				 is(customerDemographics.getCustomerDesc())
        		);
        	
			Assertions.assertEquals(customerDemographics.getCustomerTypeId(),
			 customerDemographicsEntity.getCustomerTypeId());Assertions.assertEquals(customerDemographics.getCustomerDesc(),
			 customerDemographicsEntity.getCustomerDesc());
  	}
  
  	@Test
  	public void givenAExistentCustomerDemographicsWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var customerDemographicsEntity = createCustomerDemographics("");
		UpdateCustomerDemographicsRequest customerDemographics = new UpdateCustomerDemographicsRequest();
			customerDemographics.setCustomerTypeId(customerDemographicsEntity.getCustomerTypeId());
			customerDemographics.setCustomerDesc("customerDesc");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.put(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentCustomerDemographics_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateCustomerDemographicsRequest customerDemographics = new UpdateCustomerDemographicsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.when()
        	.put(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"customerTypeId of CustomerDemographics must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentCustomerDemographics_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateCustomerDemographicsRequest customerDemographics = new UpdateCustomerDemographicsRequest();
			customerDemographics.setCustomerTypeId("customerTypeId");
			customerDemographics.setCustomerDesc("customerDesc");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerDemographics))
        	.when()
        	.put(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("customerDemographics not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentCustomerDemographics_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("customerTypeId", "customerTypeId")
 	        .delete(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentCustomerDemographics_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var customerDemographicsEntity = createCustomerDemographics("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("customerTypeId", customerDemographicsEntity.getCustomerTypeId())
 	        .delete(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findCustomerDemographicsEntityByPK(
	 customerDemographicsEntity.getCustomerTypeId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentCustomerDemographicsWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var customerDemographicsEntity = createCustomerDemographics("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("customerTypeId", customerDemographicsEntity.getCustomerTypeId())
        	.delete(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentCustomerDemographics_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var customerDemographicsEntity = createCustomerDemographics("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("customerTypeId", customerDemographicsEntity.getCustomerTypeId())
			.get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"customerDemographics.customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics.customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics.customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics.customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentCustomerDemographics_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var customerDemographicsEntity = createCustomerDemographics("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("customerTypeId", "customerTypeId")
			.get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentCustomerDemographicsWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var customerDemographicsEntity = createCustomerDemographics("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("customerTypeId", customerDemographicsEntity.getCustomerTypeId())
			.get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"customerDemographics.customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics.customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics.customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics.customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
	}
   
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset0AndLimit5_shouldReturnListOf5CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerDemographics[0]",
					 hasKey("customerTypeId"),
					"customerDemographics[0]",
					 hasKey("customerDesc") ,
					"customerDemographicsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset0AndLimit10_shouldReturnListOf10CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerDemographics[0]",
					 hasKey("customerTypeId"),
					"customerDemographics[0]",
					 hasKey("customerDesc") ,
					"customerDemographicsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset0AndLimit15_shouldReturnListOf10CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerDemographics[0]",
					 hasKey("customerTypeId"),
					"customerDemographics[0]",
					 hasKey("customerDesc") ,
					"customerDemographicsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset10AndLimit5_shouldReturnListOf5CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerDemographics[0]",
					 hasKey("customerTypeId"),
					"customerDemographics[0]",
					 hasKey("customerDesc") ,
					"customerDemographicsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset20AndLimit5_shouldReturnListOf0CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customerDemographicsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset10AndLimit10_shouldReturnListOf0CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customerDemographicsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilterWithOffset10AndLimit115_shouldReturnListOf0CustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customerDemographicsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|neq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|neq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 not(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 not(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|like|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|like|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|nlike|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|nlike|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 not(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 not(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerTypeId,customerDesc"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredCustomerDemographics() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-customerTypeId,-customerDesc"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerDemographics[0].customerTypeId",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerTypeId",
				 is(customerDemographicsEntity.getCustomerTypeId()),
				"customerDemographics[0].customerDesc",
				 Matchers.notNullValue(),
				"customerDemographics[0].customerDesc",
				 is(customerDemographicsEntity.getCustomerDesc())
				);
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeIdXXX|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDescXXX|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerTypeId,customerDesc"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eqXXX|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eqXXX|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerTypeId,customerDesc"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","customerTypeId,customerDesc"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							""+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerTypeIdXXX,customerDescXXX"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10CustomerDemographics_whenExecuteFindCustomerDemographicsByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerDemographicsEntity = createCustomerDemographics("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerDemographics(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerTypeId|eq|"+customerDemographicsEntity.getCustomerTypeId()+
							";:')"+"||"+"customerDesc|eq|"+customerDemographicsEntity.getCustomerDesc()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerTypeId,customerDesc"
	        )
	        .get(CUSTOMERDEMOGRAPHICS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
