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
import firmansyah.application.web.model.request.NewCustomerCustomerDemoRequest;
import firmansyah.application.web.model.request.UpdateCustomerCustomerDemoRequest;
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
public class CustomerCustomerDemoResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String CUSTOMERCUSTOMERDEMO_RESOURCE_PATH = API_PREFIX + "/firmansyah/customerCustomerDemo";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewCustomerCustomerDemo_whenExecuteCreateEndpoint_shouldReturnCreatedCustomerCustomerDemo201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewCustomerCustomerDemoRequest customerCustomerDemo = new NewCustomerCustomerDemoRequest();
			final var customersCustomerIdEntity= createCustomers("");
			customerCustomerDemo.setCustomerId( customersCustomerIdEntity.getCustomerId());
			final var customerDemographicsCustomerTypeIdEntity= createCustomerDemographics("");
			customerCustomerDemo.setCustomerTypeId( customerDemographicsCustomerTypeIdEntity.getCustomerTypeId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(customerCustomerDemo)).when()
		       .post(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"customerCustomerDemo.customersCustomerIdResponse.customerId",
					 Matchers.notNullValue(),
					"customerCustomerDemo.customersCustomerIdResponse.customerId",
					 is( customersCustomerIdEntity.getCustomerId()),
					"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
					 Matchers.notNullValue(),
					"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
					 is( customerDemographicsCustomerTypeIdEntity.getCustomerTypeId())
					);
  	}
  
  	@Test
  	public void givenANewCustomerCustomerDemoWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewCustomerCustomerDemoRequest customerCustomerDemo = new NewCustomerCustomerDemoRequest();
			final var customersCustomerIdEntity= createCustomers("");
			customerCustomerDemo.setCustomerId( customersCustomerIdEntity.getCustomerId());
			final var customerDemographicsCustomerTypeIdEntity= createCustomerDemographics("");
			customerCustomerDemo.setCustomerTypeId( customerDemographicsCustomerTypeIdEntity.getCustomerTypeId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.post(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidCustomerCustomerDemo_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewCustomerCustomerDemoRequest customerCustomerDemo = new NewCustomerCustomerDemoRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.when()
        	.post(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"customerId of CustomerCustomerDemo must not be blank",
						"customerTypeId of CustomerCustomerDemo must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentCustomerCustomerDemo_whenExecuteCreateEndpoint_shouldReturnConflictCustomerCustomerDemoRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
		NewCustomerCustomerDemoRequest customerCustomerDemo = new NewCustomerCustomerDemoRequest();
			customerCustomerDemo.setCustomerId(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId());
			customerCustomerDemo.setCustomerTypeId(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.post(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("customercustomerdemo already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentCustomerCustomerDemo_whenExecuteUpdateEndpoint_shouldReturnUpdatedCustomerCustomerDemo()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
		UpdateCustomerCustomerDemoRequest customerCustomerDemo = new UpdateCustomerCustomerDemoRequest();
			customerCustomerDemo.setCustomerId(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId());
			customerCustomerDemo.setCustomerTypeId(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.put(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"customerCustomerDemo.customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo.customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
        		);
        	
			Assertions.assertEquals(customerCustomerDemo.getCustomerId(),
			 customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId());Assertions.assertEquals(customerCustomerDemo.getCustomerTypeId(),
			 customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
  	}
  
  	@Test
  	public void givenAExistentCustomerCustomerDemoWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
		UpdateCustomerCustomerDemoRequest customerCustomerDemo = new UpdateCustomerCustomerDemoRequest();
			customerCustomerDemo.setCustomerId(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId());
			customerCustomerDemo.setCustomerTypeId(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.put(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentCustomerCustomerDemo_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateCustomerCustomerDemoRequest customerCustomerDemo = new UpdateCustomerCustomerDemoRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.when()
        	.put(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"customerId of CustomerCustomerDemo must not be blank",
						"customerTypeId of CustomerCustomerDemo must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentCustomerCustomerDemo_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateCustomerCustomerDemoRequest customerCustomerDemo = new UpdateCustomerCustomerDemoRequest();
			final var customersCustomerIdEntity= createCustomers("");
			customerCustomerDemo.setCustomerId( customersCustomerIdEntity.getCustomerId());
			final var customerDemographicsCustomerTypeIdEntity= createCustomerDemographics("");
			customerCustomerDemo.setCustomerTypeId( customerDemographicsCustomerTypeIdEntity.getCustomerTypeId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.when()
        	.put(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("customerCustomerDemo not found"));
  	}
  
  	@Test
  	public void givenANewCustomerCustomerDemoWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewCustomerCustomerDemoRequest customerCustomerDemo = new NewCustomerCustomerDemoRequest();
			customerCustomerDemo.setCustomerId("customerId");
			customerCustomerDemo.setCustomerTypeId("customerTypeId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customerCustomerDemo))
        	.when()
        	.post(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("customers not found"),hasItems("customerDemographics not found")));
  	}
  
   	@Test
   	public void givenAExistentCustomerCustomerDemoWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
		UpdateCustomerCustomerDemoRequest customerCustomerDemo = new UpdateCustomerCustomerDemoRequest();
			customerCustomerDemo.setCustomerId("customerId");
			customerCustomerDemo.setCustomerTypeId("customerTypeId");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(customerCustomerDemo))
         	.when()
         	.put(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("customerCustomerDemo not found"),hasItems("customers not found"),hasItems("customerDemographics not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentCustomerCustomerDemo_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("customerId", "customerId")
			.queryParam("customerTypeId", "customerTypeId")
 	        .delete(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentCustomerCustomerDemo_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("customerId", customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId())
		.queryParam("customerTypeId", customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
 	        .delete(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findCustomerCustomerDemoEntityByPK(
		 customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()
		,		     
		 customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentCustomerCustomerDemoWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		.queryParam("customerId", customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId())
		.queryParam("customerTypeId", customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
        	.delete(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentCustomerCustomerDemo_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("customerId", customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId())
		.queryParam("customerTypeId", customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
			.get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"customerCustomerDemo.customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo.customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentCustomerCustomerDemo_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var customerCustomerDemoEntity = createCustomerCustomerDemo("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("customerId", "customerId")
			.queryParam("customerTypeId", "customerTypeId")
			.get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentCustomerCustomerDemoWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var customerCustomerDemoEntity = createCustomerCustomerDemo("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		.queryParam("customerId", customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId())
		.queryParam("customerTypeId", customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
			.get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"customerCustomerDemo.customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo.customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo.customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
	}
   
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset0AndLimit5_shouldReturnListOf5CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerCustomerDemo[0]",
					 hasKey("customersCustomerIdResponse"),
					"customerCustomerDemo[0]",
					 hasKey("customerDemographicsCustomerTypeIdResponse"),
					"customerCustomerDemoCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset0AndLimit10_shouldReturnListOf10CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerCustomerDemo[0]",
					 hasKey("customersCustomerIdResponse"),
					"customerCustomerDemo[0]",
					 hasKey("customerDemographicsCustomerTypeIdResponse"),
					"customerCustomerDemoCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset0AndLimit15_shouldReturnListOf10CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerCustomerDemo[0]",
					 hasKey("customersCustomerIdResponse"),
					"customerCustomerDemo[0]",
					 hasKey("customerDemographicsCustomerTypeIdResponse"),
					"customerCustomerDemoCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset10AndLimit5_shouldReturnListOf5CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customerCustomerDemo[0]",
					 hasKey("customersCustomerIdResponse"),
					"customerCustomerDemo[0]",
					 hasKey("customerDemographicsCustomerTypeIdResponse"),
					"customerCustomerDemoCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset20AndLimit5_shouldReturnListOf0CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customerCustomerDemoCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset10AndLimit10_shouldReturnListOf0CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customerCustomerDemoCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilterWithOffset10AndLimit115_shouldReturnListOf0CustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customerCustomerDemoCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|neq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|neq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 not(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 not(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|like|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|like|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|nlike|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|nlike|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 not(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 not(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerId,customerTypeId"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredCustomerCustomerDemo() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","-customerId,-customerTypeId"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customersCustomerIdResponse.customerId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomersCustomerId().getCustomerId()),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 Matchers.notNullValue(),
				"customerCustomerDemo[0].customerDemographicsCustomerTypeIdResponse.customerTypeId",
				 is(customerCustomerDemoEntity.getPrimaryKey().getCustomerDemographicsCustomerTypeId().getCustomerTypeId())
				);
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerIdXXX|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeIdXXX|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerId,customerTypeId"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eqXXX|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eqXXX|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerId,customerTypeId"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX"
	        )
	        .queryParam("sort","customerId,customerTypeId"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerIdXXX,customerTypeIdXXX"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10CustomerCustomerDemo_whenExecuteFindCustomerCustomerDemoByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customerCustomerDemoEntity = createCustomerCustomerDemo("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomerCustomerDemo(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customerCustomerDemoEntity.getCustomersCustomerId().getCustomerId()+
							";:')"+"||"+"customerTypeId|eq|"+customerCustomerDemoEntity.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR"
	        )
	        .queryParam("sort","customerId,customerTypeId"
	        )
	        .get(CUSTOMERCUSTOMERDEMO_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
