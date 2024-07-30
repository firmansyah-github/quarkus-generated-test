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
import firmansyah.application.web.model.request.NewCustomersRequest;
import firmansyah.application.web.model.request.UpdateCustomersRequest;
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
public class CustomersResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String CUSTOMERS_RESOURCE_PATH = API_PREFIX + "/firmansyah/customers";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewCustomers_whenExecuteCreateEndpoint_shouldReturnCreatedCustomers201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewCustomersRequest customers = new NewCustomersRequest();
			customers.setCustomerId("customerId");
			customers.setCompanyName("companyName");
			customers.setContactName("contactName");
			customers.setContactTitle("contactTitle");
			customers.setAddress("address");
			customers.setCity("city");
			customers.setRegion("region");
			customers.setPostalCode("postalCode");
			customers.setCountry("country");
			customers.setPhone("phone");
			customers.setFax("fax");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(customers)).when()
		       .post(CUSTOMERS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"customers.customerId",
					 Matchers.notNullValue(),
					"customers.customerId",
					 is(customers.getCustomerId()),
					"customers.companyName",
					 Matchers.notNullValue(),
					"customers.companyName",
					 is(customers.getCompanyName()),
					"customers.contactName",
					 Matchers.notNullValue(),
					"customers.contactName",
					 is(customers.getContactName()),
					"customers.contactTitle",
					 Matchers.notNullValue(),
					"customers.contactTitle",
					 is(customers.getContactTitle()),
					"customers.address",
					 Matchers.notNullValue(),
					"customers.address",
					 is(customers.getAddress()),
					"customers.city",
					 Matchers.notNullValue(),
					"customers.city",
					 is(customers.getCity()),
					"customers.region",
					 Matchers.notNullValue(),
					"customers.region",
					 is(customers.getRegion()),
					"customers.postalCode",
					 Matchers.notNullValue(),
					"customers.postalCode",
					 is(customers.getPostalCode()),
					"customers.country",
					 Matchers.notNullValue(),
					"customers.country",
					 is(customers.getCountry()),
					"customers.phone",
					 Matchers.notNullValue(),
					"customers.phone",
					 is(customers.getPhone()),
					"customers.fax",
					 Matchers.notNullValue(),
					"customers.fax",
					 is(customers.getFax())
					);
  	}
  
  	@Test
  	public void givenANewCustomersWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewCustomersRequest customers = new NewCustomersRequest();
			customers.setCustomerId("customerId");
			customers.setCompanyName("companyName");
			customers.setContactName("contactName");
			customers.setContactTitle("contactTitle");
			customers.setAddress("address");
			customers.setCity("city");
			customers.setRegion("region");
			customers.setPostalCode("postalCode");
			customers.setCountry("country");
			customers.setPhone("phone");
			customers.setFax("fax");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(customers))
        	.post(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidCustomers_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewCustomersRequest customers = new NewCustomersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customers))
        	.when()
        	.post(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"customerId of Customers must not be blank",
						"companyName of Customers must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentCustomers_whenExecuteCreateEndpoint_shouldReturnConflictCustomersRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var customersEntity = createCustomers("");
		NewCustomersRequest customers = new NewCustomersRequest();
			customers.setCustomerId(customersEntity.getCustomerId());
			customers.setCompanyName("companyName");
			customers.setContactName("contactName");
			customers.setContactTitle("contactTitle");
			customers.setAddress("address");
			customers.setCity("city");
			customers.setRegion("region");
			customers.setPostalCode("postalCode");
			customers.setCountry("country");
			customers.setPhone("phone");
			customers.setFax("fax");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customers))
        	.post(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("customers already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentCustomers_whenExecuteUpdateEndpoint_shouldReturnUpdatedCustomers()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var customersEntity = createCustomers("");
		UpdateCustomersRequest customers = new UpdateCustomersRequest();
			customers.setCustomerId(customersEntity.getCustomerId());
			customers.setCompanyName("companyName");
			customers.setContactName("contactName");
			customers.setContactTitle("contactTitle");
			customers.setAddress("address");
			customers.setCity("city");
			customers.setRegion("region");
			customers.setPostalCode("postalCode");
			customers.setCountry("country");
			customers.setPhone("phone");
			customers.setFax("fax");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customers))
        	.put(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"customers.customerId",
				 Matchers.notNullValue(),
				"customers.customerId",
				 is(customers.getCustomerId()),
				"customers.companyName",
				 Matchers.notNullValue(),
				"customers.companyName",
				 is(customers.getCompanyName()),
				"customers.contactName",
				 Matchers.notNullValue(),
				"customers.contactName",
				 is(customers.getContactName()),
				"customers.contactTitle",
				 Matchers.notNullValue(),
				"customers.contactTitle",
				 is(customers.getContactTitle()),
				"customers.address",
				 Matchers.notNullValue(),
				"customers.address",
				 is(customers.getAddress()),
				"customers.city",
				 Matchers.notNullValue(),
				"customers.city",
				 is(customers.getCity()),
				"customers.region",
				 Matchers.notNullValue(),
				"customers.region",
				 is(customers.getRegion()),
				"customers.postalCode",
				 Matchers.notNullValue(),
				"customers.postalCode",
				 is(customers.getPostalCode()),
				"customers.country",
				 Matchers.notNullValue(),
				"customers.country",
				 is(customers.getCountry()),
				"customers.phone",
				 Matchers.notNullValue(),
				"customers.phone",
				 is(customers.getPhone()),
				"customers.fax",
				 Matchers.notNullValue(),
				"customers.fax",
				 is(customers.getFax())
        		);
        	
			Assertions.assertEquals(customers.getCustomerId(),
			 customersEntity.getCustomerId());Assertions.assertEquals(customers.getCompanyName(),
			 customersEntity.getCompanyName());Assertions.assertEquals(customers.getContactName(),
			 customersEntity.getContactName());Assertions.assertEquals(customers.getContactTitle(),
			 customersEntity.getContactTitle());Assertions.assertEquals(customers.getAddress(),
			 customersEntity.getAddress());Assertions.assertEquals(customers.getCity(),
			 customersEntity.getCity());Assertions.assertEquals(customers.getRegion(),
			 customersEntity.getRegion());Assertions.assertEquals(customers.getPostalCode(),
			 customersEntity.getPostalCode());Assertions.assertEquals(customers.getCountry(),
			 customersEntity.getCountry());Assertions.assertEquals(customers.getPhone(),
			 customersEntity.getPhone());Assertions.assertEquals(customers.getFax(),
			 customersEntity.getFax());
  	}
  
  	@Test
  	public void givenAExistentCustomersWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var customersEntity = createCustomers("");
		UpdateCustomersRequest customers = new UpdateCustomersRequest();
			customers.setCustomerId(customersEntity.getCustomerId());
			customers.setCompanyName("companyName");
			customers.setContactName("contactName");
			customers.setContactTitle("contactTitle");
			customers.setAddress("address");
			customers.setCity("city");
			customers.setRegion("region");
			customers.setPostalCode("postalCode");
			customers.setCountry("country");
			customers.setPhone("phone");
			customers.setFax("fax");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(customers))
        	.put(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentCustomers_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateCustomersRequest customers = new UpdateCustomersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customers))
        	.when()
        	.put(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"customerId of Customers must not be blank",
						"companyName of Customers must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentCustomers_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateCustomersRequest customers = new UpdateCustomersRequest();
			customers.setCustomerId("customerId");
			customers.setCompanyName("companyName");
			customers.setContactName("contactName");
			customers.setContactTitle("contactTitle");
			customers.setAddress("address");
			customers.setCity("city");
			customers.setRegion("region");
			customers.setPostalCode("postalCode");
			customers.setCountry("country");
			customers.setPhone("phone");
			customers.setFax("fax");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(customers))
        	.when()
        	.put(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("customers not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentCustomers_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("customerId", "customerId")
 	        .delete(CUSTOMERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentCustomers_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var customersEntity = createCustomers("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("customerId", customersEntity.getCustomerId())
 	        .delete(CUSTOMERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findCustomersEntityByPK(
	 customersEntity.getCustomerId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentCustomersWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var customersEntity = createCustomers("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("customerId", customersEntity.getCustomerId())
        	.delete(CUSTOMERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentCustomers_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var customersEntity = createCustomers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("customerId", customersEntity.getCustomerId())
			.get(CUSTOMERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"customers.customerId",
				 Matchers.notNullValue(),
				"customers.customerId",
				 is(customersEntity.getCustomerId()),
				"customers.companyName",
				 Matchers.notNullValue(),
				"customers.companyName",
				 is(customersEntity.getCompanyName()),
				"customers.contactName",
				 Matchers.notNullValue(),
				"customers.contactName",
				 is(customersEntity.getContactName()),
				"customers.contactTitle",
				 Matchers.notNullValue(),
				"customers.contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers.address",
				 Matchers.notNullValue(),
				"customers.address",
				 is(customersEntity.getAddress()),
				"customers.city",
				 Matchers.notNullValue(),
				"customers.city",
				 is(customersEntity.getCity()),
				"customers.region",
				 Matchers.notNullValue(),
				"customers.region",
				 is(customersEntity.getRegion()),
				"customers.postalCode",
				 Matchers.notNullValue(),
				"customers.postalCode",
				 is(customersEntity.getPostalCode()),
				"customers.country",
				 Matchers.notNullValue(),
				"customers.country",
				 is(customersEntity.getCountry()),
				"customers.phone",
				 Matchers.notNullValue(),
				"customers.phone",
				 is(customersEntity.getPhone()),
				"customers.fax",
				 Matchers.notNullValue(),
				"customers.fax",
				 is(customersEntity.getFax())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentCustomers_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var customersEntity = createCustomers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("customerId", "customerId")
			.get(CUSTOMERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentCustomersWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var customersEntity = createCustomers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("customerId", customersEntity.getCustomerId())
			.get(CUSTOMERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"customers.customerId",
				 Matchers.notNullValue(),
				"customers.customerId",
				 is(customersEntity.getCustomerId()),
				"customers.companyName",
				 Matchers.notNullValue(),
				"customers.companyName",
				 is(customersEntity.getCompanyName()),
				"customers.contactName",
				 Matchers.notNullValue(),
				"customers.contactName",
				 is(customersEntity.getContactName()),
				"customers.contactTitle",
				 Matchers.notNullValue(),
				"customers.contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers.address",
				 Matchers.notNullValue(),
				"customers.address",
				 is(customersEntity.getAddress()),
				"customers.city",
				 Matchers.notNullValue(),
				"customers.city",
				 is(customersEntity.getCity()),
				"customers.region",
				 Matchers.notNullValue(),
				"customers.region",
				 is(customersEntity.getRegion()),
				"customers.postalCode",
				 Matchers.notNullValue(),
				"customers.postalCode",
				 is(customersEntity.getPostalCode()),
				"customers.country",
				 Matchers.notNullValue(),
				"customers.country",
				 is(customersEntity.getCountry()),
				"customers.phone",
				 Matchers.notNullValue(),
				"customers.phone",
				 is(customersEntity.getPhone()),
				"customers.fax",
				 Matchers.notNullValue(),
				"customers.fax",
				 is(customersEntity.getFax())
				);
	}
   
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilterWithOffset0AndLimit5_shouldReturnListOf5Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customers[0]",
					 hasKey("customerId"),
					"customers[0]",
					 hasKey("companyName"),
					"customers[0]",
					 hasKey("contactName"),
					"customers[0]",
					 hasKey("contactTitle"),
					"customers[0]",
					 hasKey("address"),
					"customers[0]",
					 hasKey("city"),
					"customers[0]",
					 hasKey("region"),
					"customers[0]",
					 hasKey("postalCode"),
					"customers[0]",
					 hasKey("country"),
					"customers[0]",
					 hasKey("phone"),
					"customers[0]",
					 hasKey("fax") ,
					"customersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilterWithOffset0AndLimit10_shouldReturnListOf10Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customers[0]",
					 hasKey("customerId"),
					"customers[0]",
					 hasKey("companyName"),
					"customers[0]",
					 hasKey("contactName"),
					"customers[0]",
					 hasKey("contactTitle"),
					"customers[0]",
					 hasKey("address"),
					"customers[0]",
					 hasKey("city"),
					"customers[0]",
					 hasKey("region"),
					"customers[0]",
					 hasKey("postalCode"),
					"customers[0]",
					 hasKey("country"),
					"customers[0]",
					 hasKey("phone"),
					"customers[0]",
					 hasKey("fax") ,
					"customersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilterWithOffset0AndLimit15_shouldReturnListOf10Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customers[0]",
					 hasKey("customerId"),
					"customers[0]",
					 hasKey("companyName"),
					"customers[0]",
					 hasKey("contactName"),
					"customers[0]",
					 hasKey("contactTitle"),
					"customers[0]",
					 hasKey("address"),
					"customers[0]",
					 hasKey("city"),
					"customers[0]",
					 hasKey("region"),
					"customers[0]",
					 hasKey("postalCode"),
					"customers[0]",
					 hasKey("country"),
					"customers[0]",
					 hasKey("phone"),
					"customers[0]",
					 hasKey("fax") ,
					"customersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Customers_whenExecuteFindCustomersByFilterWithOffset10AndLimit5_shouldReturnListOf5Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"customers[0]",
					 hasKey("customerId"),
					"customers[0]",
					 hasKey("companyName"),
					"customers[0]",
					 hasKey("contactName"),
					"customers[0]",
					 hasKey("contactTitle"),
					"customers[0]",
					 hasKey("address"),
					"customers[0]",
					 hasKey("city"),
					"customers[0]",
					 hasKey("region"),
					"customers[0]",
					 hasKey("postalCode"),
					"customers[0]",
					 hasKey("country"),
					"customers[0]",
					 hasKey("phone"),
					"customers[0]",
					 hasKey("fax") ,
					"customersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilterWithOffset20AndLimit5_shouldReturnListOf0Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilterWithOffset10AndLimit10_shouldReturnListOf0Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilterWithOffset10AndLimit115_shouldReturnListOf0Customers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCustomers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"customersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							""+"||"+"address|eq|"+customersEntity.getAddress()+
							""+"||"+"city|eq|"+customersEntity.getCity()+
							""+"||"+"region|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							""+"||"+"country|eq|"+customersEntity.getCountry()+
							""+"||"+"phone|eq|"+customersEntity.getPhone()+
							""+"||"+"fax|eq|"+customersEntity.getFax()+
							""

	        )  
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 is(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 is(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 is(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 is(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 is(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 is(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 is(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 is(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 is(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 is(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							""+"||"+"address|eq|"+customersEntity.getAddress()+
							""+"||"+"city|eq|"+customersEntity.getCity()+
							""+"||"+"region|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							""+"||"+"country|eq|"+customersEntity.getCountry()+
							""+"||"+"phone|eq|"+customersEntity.getPhone()+
							""+"||"+"fax|eq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 is(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 is(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 is(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 is(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 is(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 is(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 is(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 is(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 is(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 is(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|neq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|neq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|neq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|neq|"+customersEntity.getContactTitle()+
							""+"||"+"address|neq|"+customersEntity.getAddress()+
							""+"||"+"city|neq|"+customersEntity.getCity()+
							""+"||"+"region|neq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|neq|"+customersEntity.getPostalCode()+
							""+"||"+"country|neq|"+customersEntity.getCountry()+
							""+"||"+"phone|neq|"+customersEntity.getPhone()+
							""+"||"+"fax|neq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 not(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 not(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 not(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 not(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 not(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 not(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 not(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 not(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 not(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 not(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 not(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|like|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|like|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|like|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|like|"+customersEntity.getContactTitle()+
							""+"||"+"address|like|"+customersEntity.getAddress()+
							""+"||"+"city|like|"+customersEntity.getCity()+
							""+"||"+"region|like|"+customersEntity.getRegion()+
							""+"||"+"postalCode|like|"+customersEntity.getPostalCode()+
							""+"||"+"country|like|"+customersEntity.getCountry()+
							""+"||"+"phone|like|"+customersEntity.getPhone()+
							""+"||"+"fax|like|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 is(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 is(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 is(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 is(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 is(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 is(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 is(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 is(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 is(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 is(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|nlike|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|nlike|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|nlike|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|nlike|"+customersEntity.getContactTitle()+
							""+"||"+"address|nlike|"+customersEntity.getAddress()+
							""+"||"+"city|nlike|"+customersEntity.getCity()+
							""+"||"+"region|nlike|"+customersEntity.getRegion()+
							""+"||"+"postalCode|nlike|"+customersEntity.getPostalCode()+
							""+"||"+"country|nlike|"+customersEntity.getCountry()+
							""+"||"+"phone|nlike|"+customersEntity.getPhone()+
							""+"||"+"fax|nlike|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 not(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 not(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 not(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 not(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 not(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 not(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 not(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 not(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 not(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 not(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 not(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							""+"||"+"address|eq|"+customersEntity.getAddress()+
							""+"||"+"city|eq|"+customersEntity.getCity()+
							""+"||"+"region|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							""+"||"+"country|eq|"+customersEntity.getCountry()+
							""+"||"+"phone|eq|"+customersEntity.getPhone()+
							""+"||"+"fax|eq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","customerId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 is(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 is(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 is(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 is(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 is(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 is(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 is(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 is(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 is(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 is(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredCustomers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							""+"||"+"address|eq|"+customersEntity.getAddress()+
							""+"||"+"city|eq|"+customersEntity.getCity()+
							""+"||"+"region|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							""+"||"+"country|eq|"+customersEntity.getCountry()+
							""+"||"+"phone|eq|"+customersEntity.getPhone()+
							""+"||"+"fax|eq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-customerId,-companyName,-contactName,-contactTitle,-address,-city,-region,-postalCode,-country,-phone,-fax"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"customers[0].customerId",
				 Matchers.notNullValue(),
				"customers[0].customerId",
				 is(customersEntity.getCustomerId()),
				"customers[0].companyName",
				 Matchers.notNullValue(),
				"customers[0].companyName",
				 is(customersEntity.getCompanyName()),
				"customers[0].contactName",
				 Matchers.notNullValue(),
				"customers[0].contactName",
				 is(customersEntity.getContactName()),
				"customers[0].contactTitle",
				 Matchers.notNullValue(),
				"customers[0].contactTitle",
				 is(customersEntity.getContactTitle()),
				"customers[0].address",
				 Matchers.notNullValue(),
				"customers[0].address",
				 is(customersEntity.getAddress()),
				"customers[0].city",
				 Matchers.notNullValue(),
				"customers[0].city",
				 is(customersEntity.getCity()),
				"customers[0].region",
				 Matchers.notNullValue(),
				"customers[0].region",
				 is(customersEntity.getRegion()),
				"customers[0].postalCode",
				 Matchers.notNullValue(),
				"customers[0].postalCode",
				 is(customersEntity.getPostalCode()),
				"customers[0].country",
				 Matchers.notNullValue(),
				"customers[0].country",
				 is(customersEntity.getCountry()),
				"customers[0].phone",
				 Matchers.notNullValue(),
				"customers[0].phone",
				 is(customersEntity.getPhone()),
				"customers[0].fax",
				 Matchers.notNullValue(),
				"customers[0].fax",
				 is(customersEntity.getFax())
				);
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerIdXXX|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyNameXXX|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactNameXXX|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitleXXX|eq|"+customersEntity.getContactTitle()+
							""+"||"+"addressXXX|eq|"+customersEntity.getAddress()+
							""+"||"+"cityXXX|eq|"+customersEntity.getCity()+
							""+"||"+"regionXXX|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCodeXXX|eq|"+customersEntity.getPostalCode()+
							""+"||"+"countryXXX|eq|"+customersEntity.getCountry()+
							""+"||"+"phoneXXX|eq|"+customersEntity.getPhone()+
							""+"||"+"faxXXX|eq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","customerId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eqXXX|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eqXXX|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eqXXX|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eqXXX|"+customersEntity.getContactTitle()+
							""+"||"+"address|eqXXX|"+customersEntity.getAddress()+
							""+"||"+"city|eqXXX|"+customersEntity.getCity()+
							""+"||"+"region|eqXXX|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eqXXX|"+customersEntity.getPostalCode()+
							""+"||"+"country|eqXXX|"+customersEntity.getCountry()+
							""+"||"+"phone|eqXXX|"+customersEntity.getPhone()+
							""+"||"+"fax|eqXXX|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","customerId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							""+"||"+"address|eq|"+customersEntity.getAddress()+
							""+"||"+"city|eq|"+customersEntity.getCity()+
							""+"||"+"region|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							""+"||"+"country|eq|"+customersEntity.getCountry()+
							""+"||"+"phone|eq|"+customersEntity.getPhone()+
							""+"||"+"fax|eq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","customerId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							""+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+customersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							""+"||"+"address|eq|"+customersEntity.getAddress()+
							""+"||"+"city|eq|"+customersEntity.getCity()+
							""+"||"+"region|eq|"+customersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							""+"||"+"country|eq|"+customersEntity.getCountry()+
							""+"||"+"phone|eq|"+customersEntity.getPhone()+
							""+"||"+"fax|eq|"+customersEntity.getFax()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","customerIdXXX,companyNameXXX,contactNameXXX,contactTitleXXX,addressXXX,cityXXX,regionXXX,postalCodeXXX,countryXXX,phoneXXX,faxXXX"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Customers_whenExecuteFindCustomersByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var customersEntity = createCustomers("");
	    for (int i = 0; i < 9; i++) {
	    	createCustomers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "customerId|eq|"+customersEntity.getCustomerId()+
							";:')"+"||"+"companyName|eq|"+customersEntity.getCompanyName()+
							";:')"+"||"+"contactName|eq|"+customersEntity.getContactName()+
							";:')"+"||"+"contactTitle|eq|"+customersEntity.getContactTitle()+
							";:')"+"||"+"address|eq|"+customersEntity.getAddress()+
							";:')"+"||"+"city|eq|"+customersEntity.getCity()+
							";:')"+"||"+"region|eq|"+customersEntity.getRegion()+
							";:')"+"||"+"postalCode|eq|"+customersEntity.getPostalCode()+
							";:')"+"||"+"country|eq|"+customersEntity.getCountry()+
							";:')"+"||"+"phone|eq|"+customersEntity.getPhone()+
							";:')"+"||"+"fax|eq|"+customersEntity.getFax()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","customerId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax"
	        )
	        .get(CUSTOMERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
