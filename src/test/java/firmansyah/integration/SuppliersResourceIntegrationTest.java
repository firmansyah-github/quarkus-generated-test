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
import firmansyah.application.web.model.request.NewSuppliersRequest;
import firmansyah.application.web.model.request.UpdateSuppliersRequest;
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
public class SuppliersResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String SUPPLIERS_RESOURCE_PATH = API_PREFIX + "/firmansyah/suppliers";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewSuppliers_whenExecuteCreateEndpoint_shouldReturnCreatedSuppliers201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewSuppliersRequest suppliers = new NewSuppliersRequest();
			suppliers.setSupplierId(2);
			suppliers.setCompanyName("companyName");
			suppliers.setContactName("contactName");
			suppliers.setContactTitle("contactTitle");
			suppliers.setAddress("address");
			suppliers.setCity("city");
			suppliers.setRegion("region");
			suppliers.setPostalCode("postalCode");
			suppliers.setCountry("country");
			suppliers.setPhone("phone");
			suppliers.setFax("fax");
			suppliers.setHomepage("homepage");
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(suppliers)).when()
		       .post(SUPPLIERS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"suppliers.supplierId",
					 Matchers.notNullValue(),
					"suppliers.supplierId",
					 is(suppliers.getSupplierId()),
					"suppliers.companyName",
					 Matchers.notNullValue(),
					"suppliers.companyName",
					 is(suppliers.getCompanyName()),
					"suppliers.contactName",
					 Matchers.notNullValue(),
					"suppliers.contactName",
					 is(suppliers.getContactName()),
					"suppliers.contactTitle",
					 Matchers.notNullValue(),
					"suppliers.contactTitle",
					 is(suppliers.getContactTitle()),
					"suppliers.address",
					 Matchers.notNullValue(),
					"suppliers.address",
					 is(suppliers.getAddress()),
					"suppliers.city",
					 Matchers.notNullValue(),
					"suppliers.city",
					 is(suppliers.getCity()),
					"suppliers.region",
					 Matchers.notNullValue(),
					"suppliers.region",
					 is(suppliers.getRegion()),
					"suppliers.postalCode",
					 Matchers.notNullValue(),
					"suppliers.postalCode",
					 is(suppliers.getPostalCode()),
					"suppliers.country",
					 Matchers.notNullValue(),
					"suppliers.country",
					 is(suppliers.getCountry()),
					"suppliers.phone",
					 Matchers.notNullValue(),
					"suppliers.phone",
					 is(suppliers.getPhone()),
					"suppliers.fax",
					 Matchers.notNullValue(),
					"suppliers.fax",
					 is(suppliers.getFax()),
					"suppliers.homepage",
					 Matchers.notNullValue(),
					"suppliers.homepage",
					 is(suppliers.getHomepage())
					);
  	}
  
  	@Test
  	public void givenANewSuppliersWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewSuppliersRequest suppliers = new NewSuppliersRequest();
			suppliers.setSupplierId(2);
			suppliers.setCompanyName("companyName");
			suppliers.setContactName("contactName");
			suppliers.setContactTitle("contactTitle");
			suppliers.setAddress("address");
			suppliers.setCity("city");
			suppliers.setRegion("region");
			suppliers.setPostalCode("postalCode");
			suppliers.setCountry("country");
			suppliers.setPhone("phone");
			suppliers.setFax("fax");
			suppliers.setHomepage("homepage");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.post(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidSuppliers_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewSuppliersRequest suppliers = new NewSuppliersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.when()
        	.post(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"supplierId of Suppliers must not be blank",
						"companyName of Suppliers must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentSuppliers_whenExecuteCreateEndpoint_shouldReturnConflictSuppliersRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var suppliersEntity = createSuppliers("");
		NewSuppliersRequest suppliers = new NewSuppliersRequest();
			suppliers.setSupplierId(suppliersEntity.getSupplierId());
			suppliers.setCompanyName("companyName");
			suppliers.setContactName("contactName");
			suppliers.setContactTitle("contactTitle");
			suppliers.setAddress("address");
			suppliers.setCity("city");
			suppliers.setRegion("region");
			suppliers.setPostalCode("postalCode");
			suppliers.setCountry("country");
			suppliers.setPhone("phone");
			suppliers.setFax("fax");
			suppliers.setHomepage("homepage");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.post(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("suppliers already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentSuppliers_whenExecuteUpdateEndpoint_shouldReturnUpdatedSuppliers()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var suppliersEntity = createSuppliers("");
		UpdateSuppliersRequest suppliers = new UpdateSuppliersRequest();
			suppliers.setSupplierId(suppliersEntity.getSupplierId());
			suppliers.setCompanyName("companyName");
			suppliers.setContactName("contactName");
			suppliers.setContactTitle("contactTitle");
			suppliers.setAddress("address");
			suppliers.setCity("city");
			suppliers.setRegion("region");
			suppliers.setPostalCode("postalCode");
			suppliers.setCountry("country");
			suppliers.setPhone("phone");
			suppliers.setFax("fax");
			suppliers.setHomepage("homepage");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.put(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"suppliers.supplierId",
				 Matchers.notNullValue(),
				"suppliers.supplierId",
				 is(suppliers.getSupplierId()),
				"suppliers.companyName",
				 Matchers.notNullValue(),
				"suppliers.companyName",
				 is(suppliers.getCompanyName()),
				"suppliers.contactName",
				 Matchers.notNullValue(),
				"suppliers.contactName",
				 is(suppliers.getContactName()),
				"suppliers.contactTitle",
				 Matchers.notNullValue(),
				"suppliers.contactTitle",
				 is(suppliers.getContactTitle()),
				"suppliers.address",
				 Matchers.notNullValue(),
				"suppliers.address",
				 is(suppliers.getAddress()),
				"suppliers.city",
				 Matchers.notNullValue(),
				"suppliers.city",
				 is(suppliers.getCity()),
				"suppliers.region",
				 Matchers.notNullValue(),
				"suppliers.region",
				 is(suppliers.getRegion()),
				"suppliers.postalCode",
				 Matchers.notNullValue(),
				"suppliers.postalCode",
				 is(suppliers.getPostalCode()),
				"suppliers.country",
				 Matchers.notNullValue(),
				"suppliers.country",
				 is(suppliers.getCountry()),
				"suppliers.phone",
				 Matchers.notNullValue(),
				"suppliers.phone",
				 is(suppliers.getPhone()),
				"suppliers.fax",
				 Matchers.notNullValue(),
				"suppliers.fax",
				 is(suppliers.getFax()),
				"suppliers.homepage",
				 Matchers.notNullValue(),
				"suppliers.homepage",
				 is(suppliers.getHomepage())
        		);
        	
			Assertions.assertEquals(suppliers.getSupplierId(),
			 suppliersEntity.getSupplierId());Assertions.assertEquals(suppliers.getCompanyName(),
			 suppliersEntity.getCompanyName());Assertions.assertEquals(suppliers.getContactName(),
			 suppliersEntity.getContactName());Assertions.assertEquals(suppliers.getContactTitle(),
			 suppliersEntity.getContactTitle());Assertions.assertEquals(suppliers.getAddress(),
			 suppliersEntity.getAddress());Assertions.assertEquals(suppliers.getCity(),
			 suppliersEntity.getCity());Assertions.assertEquals(suppliers.getRegion(),
			 suppliersEntity.getRegion());Assertions.assertEquals(suppliers.getPostalCode(),
			 suppliersEntity.getPostalCode());Assertions.assertEquals(suppliers.getCountry(),
			 suppliersEntity.getCountry());Assertions.assertEquals(suppliers.getPhone(),
			 suppliersEntity.getPhone());Assertions.assertEquals(suppliers.getFax(),
			 suppliersEntity.getFax());Assertions.assertEquals(suppliers.getHomepage(),
			 suppliersEntity.getHomepage());
  	}
  
  	@Test
  	public void givenAExistentSuppliersWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var suppliersEntity = createSuppliers("");
		UpdateSuppliersRequest suppliers = new UpdateSuppliersRequest();
			suppliers.setSupplierId(suppliersEntity.getSupplierId());
			suppliers.setCompanyName("companyName");
			suppliers.setContactName("contactName");
			suppliers.setContactTitle("contactTitle");
			suppliers.setAddress("address");
			suppliers.setCity("city");
			suppliers.setRegion("region");
			suppliers.setPostalCode("postalCode");
			suppliers.setCountry("country");
			suppliers.setPhone("phone");
			suppliers.setFax("fax");
			suppliers.setHomepage("homepage");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.put(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentSuppliers_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateSuppliersRequest suppliers = new UpdateSuppliersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.when()
        	.put(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"supplierId of Suppliers must not be blank",
						"companyName of Suppliers must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentSuppliers_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateSuppliersRequest suppliers = new UpdateSuppliersRequest();
			suppliers.setSupplierId(2);
			suppliers.setCompanyName("companyName");
			suppliers.setContactName("contactName");
			suppliers.setContactTitle("contactTitle");
			suppliers.setAddress("address");
			suppliers.setCity("city");
			suppliers.setRegion("region");
			suppliers.setPostalCode("postalCode");
			suppliers.setCountry("country");
			suppliers.setPhone("phone");
			suppliers.setFax("fax");
			suppliers.setHomepage("homepage");
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(suppliers))
        	.when()
        	.put(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("suppliers not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentSuppliers_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("supplierId", 2)
 	        .delete(SUPPLIERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentSuppliers_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var suppliersEntity = createSuppliers("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("supplierId", suppliersEntity.getSupplierId())
 	        .delete(SUPPLIERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findSuppliersEntityByPK(
	 suppliersEntity.getSupplierId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentSuppliersWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var suppliersEntity = createSuppliers("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("supplierId", suppliersEntity.getSupplierId())
        	.delete(SUPPLIERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentSuppliers_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var suppliersEntity = createSuppliers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("supplierId", suppliersEntity.getSupplierId())
			.get(SUPPLIERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"suppliers.supplierId",
				 Matchers.notNullValue(),
				"suppliers.supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers.companyName",
				 Matchers.notNullValue(),
				"suppliers.companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers.contactName",
				 Matchers.notNullValue(),
				"suppliers.contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers.contactTitle",
				 Matchers.notNullValue(),
				"suppliers.contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers.address",
				 Matchers.notNullValue(),
				"suppliers.address",
				 is(suppliersEntity.getAddress()),
				"suppliers.city",
				 Matchers.notNullValue(),
				"suppliers.city",
				 is(suppliersEntity.getCity()),
				"suppliers.region",
				 Matchers.notNullValue(),
				"suppliers.region",
				 is(suppliersEntity.getRegion()),
				"suppliers.postalCode",
				 Matchers.notNullValue(),
				"suppliers.postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers.country",
				 Matchers.notNullValue(),
				"suppliers.country",
				 is(suppliersEntity.getCountry()),
				"suppliers.phone",
				 Matchers.notNullValue(),
				"suppliers.phone",
				 is(suppliersEntity.getPhone()),
				"suppliers.fax",
				 Matchers.notNullValue(),
				"suppliers.fax",
				 is(suppliersEntity.getFax()),
				"suppliers.homepage",
				 Matchers.notNullValue(),
				"suppliers.homepage",
				 is(suppliersEntity.getHomepage())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentSuppliers_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var suppliersEntity = createSuppliers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("supplierId", 2)
			.get(SUPPLIERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentSuppliersWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var suppliersEntity = createSuppliers("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("supplierId", suppliersEntity.getSupplierId())
			.get(SUPPLIERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"suppliers.supplierId",
				 Matchers.notNullValue(),
				"suppliers.supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers.companyName",
				 Matchers.notNullValue(),
				"suppliers.companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers.contactName",
				 Matchers.notNullValue(),
				"suppliers.contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers.contactTitle",
				 Matchers.notNullValue(),
				"suppliers.contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers.address",
				 Matchers.notNullValue(),
				"suppliers.address",
				 is(suppliersEntity.getAddress()),
				"suppliers.city",
				 Matchers.notNullValue(),
				"suppliers.city",
				 is(suppliersEntity.getCity()),
				"suppliers.region",
				 Matchers.notNullValue(),
				"suppliers.region",
				 is(suppliersEntity.getRegion()),
				"suppliers.postalCode",
				 Matchers.notNullValue(),
				"suppliers.postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers.country",
				 Matchers.notNullValue(),
				"suppliers.country",
				 is(suppliersEntity.getCountry()),
				"suppliers.phone",
				 Matchers.notNullValue(),
				"suppliers.phone",
				 is(suppliersEntity.getPhone()),
				"suppliers.fax",
				 Matchers.notNullValue(),
				"suppliers.fax",
				 is(suppliersEntity.getFax()),
				"suppliers.homepage",
				 Matchers.notNullValue(),
				"suppliers.homepage",
				 is(suppliersEntity.getHomepage())
				);
	}
   
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilterWithOffset0AndLimit5_shouldReturnListOf5Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"suppliers[0]",
					 hasKey("supplierId"),
					"suppliers[0]",
					 hasKey("companyName"),
					"suppliers[0]",
					 hasKey("contactName"),
					"suppliers[0]",
					 hasKey("contactTitle"),
					"suppliers[0]",
					 hasKey("address"),
					"suppliers[0]",
					 hasKey("city"),
					"suppliers[0]",
					 hasKey("region"),
					"suppliers[0]",
					 hasKey("postalCode"),
					"suppliers[0]",
					 hasKey("country"),
					"suppliers[0]",
					 hasKey("phone"),
					"suppliers[0]",
					 hasKey("fax"),
					"suppliers[0]",
					 hasKey("homepage") ,
					"suppliersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilterWithOffset0AndLimit10_shouldReturnListOf10Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"suppliers[0]",
					 hasKey("supplierId"),
					"suppliers[0]",
					 hasKey("companyName"),
					"suppliers[0]",
					 hasKey("contactName"),
					"suppliers[0]",
					 hasKey("contactTitle"),
					"suppliers[0]",
					 hasKey("address"),
					"suppliers[0]",
					 hasKey("city"),
					"suppliers[0]",
					 hasKey("region"),
					"suppliers[0]",
					 hasKey("postalCode"),
					"suppliers[0]",
					 hasKey("country"),
					"suppliers[0]",
					 hasKey("phone"),
					"suppliers[0]",
					 hasKey("fax"),
					"suppliers[0]",
					 hasKey("homepage") ,
					"suppliersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilterWithOffset0AndLimit15_shouldReturnListOf10Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"suppliers[0]",
					 hasKey("supplierId"),
					"suppliers[0]",
					 hasKey("companyName"),
					"suppliers[0]",
					 hasKey("contactName"),
					"suppliers[0]",
					 hasKey("contactTitle"),
					"suppliers[0]",
					 hasKey("address"),
					"suppliers[0]",
					 hasKey("city"),
					"suppliers[0]",
					 hasKey("region"),
					"suppliers[0]",
					 hasKey("postalCode"),
					"suppliers[0]",
					 hasKey("country"),
					"suppliers[0]",
					 hasKey("phone"),
					"suppliers[0]",
					 hasKey("fax"),
					"suppliers[0]",
					 hasKey("homepage") ,
					"suppliersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Suppliers_whenExecuteFindSuppliersByFilterWithOffset10AndLimit5_shouldReturnListOf5Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"suppliers[0]",
					 hasKey("supplierId"),
					"suppliers[0]",
					 hasKey("companyName"),
					"suppliers[0]",
					 hasKey("contactName"),
					"suppliers[0]",
					 hasKey("contactTitle"),
					"suppliers[0]",
					 hasKey("address"),
					"suppliers[0]",
					 hasKey("city"),
					"suppliers[0]",
					 hasKey("region"),
					"suppliers[0]",
					 hasKey("postalCode"),
					"suppliers[0]",
					 hasKey("country"),
					"suppliers[0]",
					 hasKey("phone"),
					"suppliers[0]",
					 hasKey("fax"),
					"suppliers[0]",
					 hasKey("homepage") ,
					"suppliersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilterWithOffset20AndLimit5_shouldReturnListOf0Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"suppliersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilterWithOffset10AndLimit10_shouldReturnListOf0Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"suppliersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilterWithOffset10AndLimit115_shouldReturnListOf0Suppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createSuppliers(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"suppliersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eq|"+suppliersEntity.getAddress()+
							""+"||"+"city|eq|"+suppliersEntity.getCity()+
							""+"||"+"region|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 is(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 is(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 is(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 is(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 is(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 is(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 is(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eq|"+suppliersEntity.getAddress()+
							""+"||"+"city|eq|"+suppliersEntity.getCity()+
							""+"||"+"region|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 is(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 is(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 is(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 is(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 is(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 is(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 is(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|neq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|neq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|neq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|neq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|neq|"+suppliersEntity.getAddress()+
							""+"||"+"city|neq|"+suppliersEntity.getCity()+
							""+"||"+"region|neq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|neq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|neq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|neq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|neq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|neq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 not(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 not(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 not(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 not(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 not(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 not(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 not(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 not(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 not(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 not(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 not(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 not(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|like|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|like|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|like|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|like|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|like|"+suppliersEntity.getAddress()+
							""+"||"+"city|like|"+suppliersEntity.getCity()+
							""+"||"+"region|like|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|like|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|like|"+suppliersEntity.getCountry()+
							""+"||"+"phone|like|"+suppliersEntity.getPhone()+
							""+"||"+"fax|like|"+suppliersEntity.getFax()+
							""+"||"+"homepage|like|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 is(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 is(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 is(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 is(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 is(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 is(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 is(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|nlike|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|nlike|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|nlike|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|nlike|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|nlike|"+suppliersEntity.getAddress()+
							""+"||"+"city|nlike|"+suppliersEntity.getCity()+
							""+"||"+"region|nlike|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|nlike|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|nlike|"+suppliersEntity.getCountry()+
							""+"||"+"phone|nlike|"+suppliersEntity.getPhone()+
							""+"||"+"fax|nlike|"+suppliersEntity.getFax()+
							""+"||"+"homepage|nlike|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 not(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 not(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 not(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 not(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 not(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 not(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 not(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 not(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 not(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 not(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 not(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 not(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eq|"+suppliersEntity.getAddress()+
							""+"||"+"city|eq|"+suppliersEntity.getCity()+
							""+"||"+"region|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","supplierId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax,homepage"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 is(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 is(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 is(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 is(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 is(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 is(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 is(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredSuppliers() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eq|"+suppliersEntity.getAddress()+
							""+"||"+"city|eq|"+suppliersEntity.getCity()+
							""+"||"+"region|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-supplierId,-companyName,-contactName,-contactTitle,-address,-city,-region,-postalCode,-country,-phone,-fax,-homepage"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"suppliers[0].supplierId",
				 Matchers.notNullValue(),
				"suppliers[0].supplierId",
				 is(suppliersEntity.getSupplierId()),
				"suppliers[0].companyName",
				 Matchers.notNullValue(),
				"suppliers[0].companyName",
				 is(suppliersEntity.getCompanyName()),
				"suppliers[0].contactName",
				 Matchers.notNullValue(),
				"suppliers[0].contactName",
				 is(suppliersEntity.getContactName()),
				"suppliers[0].contactTitle",
				 Matchers.notNullValue(),
				"suppliers[0].contactTitle",
				 is(suppliersEntity.getContactTitle()),
				"suppliers[0].address",
				 Matchers.notNullValue(),
				"suppliers[0].address",
				 is(suppliersEntity.getAddress()),
				"suppliers[0].city",
				 Matchers.notNullValue(),
				"suppliers[0].city",
				 is(suppliersEntity.getCity()),
				"suppliers[0].region",
				 Matchers.notNullValue(),
				"suppliers[0].region",
				 is(suppliersEntity.getRegion()),
				"suppliers[0].postalCode",
				 Matchers.notNullValue(),
				"suppliers[0].postalCode",
				 is(suppliersEntity.getPostalCode()),
				"suppliers[0].country",
				 Matchers.notNullValue(),
				"suppliers[0].country",
				 is(suppliersEntity.getCountry()),
				"suppliers[0].phone",
				 Matchers.notNullValue(),
				"suppliers[0].phone",
				 is(suppliersEntity.getPhone()),
				"suppliers[0].fax",
				 Matchers.notNullValue(),
				"suppliers[0].fax",
				 is(suppliersEntity.getFax()),
				"suppliers[0].homepage",
				 Matchers.notNullValue(),
				"suppliers[0].homepage",
				 is(suppliersEntity.getHomepage())
				);
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierIdXXX|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyNameXXX|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactNameXXX|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitleXXX|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"addressXXX|eq|"+suppliersEntity.getAddress()+
							""+"||"+"cityXXX|eq|"+suppliersEntity.getCity()+
							""+"||"+"regionXXX|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCodeXXX|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"countryXXX|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phoneXXX|eq|"+suppliersEntity.getPhone()+
							""+"||"+"faxXXX|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepageXXX|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","supplierId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax,homepage"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eqXXX|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eqXXX|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eqXXX|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eqXXX|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eqXXX|"+suppliersEntity.getAddress()+
							""+"||"+"city|eqXXX|"+suppliersEntity.getCity()+
							""+"||"+"region|eqXXX|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eqXXX|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eqXXX|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eqXXX|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eqXXX|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eqXXX|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","supplierId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax,homepage"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eq|"+suppliersEntity.getAddress()+
							""+"||"+"city|eq|"+suppliersEntity.getCity()+
							""+"||"+"region|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","supplierId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax,homepage"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							""+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							""+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							""+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							""+"||"+"address|eq|"+suppliersEntity.getAddress()+
							""+"||"+"city|eq|"+suppliersEntity.getCity()+
							""+"||"+"region|eq|"+suppliersEntity.getRegion()+
							""+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							""+"||"+"country|eq|"+suppliersEntity.getCountry()+
							""+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							""+"||"+"fax|eq|"+suppliersEntity.getFax()+
							""+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","supplierIdXXX,companyNameXXX,contactNameXXX,contactTitleXXX,addressXXX,cityXXX,regionXXX,postalCodeXXX,countryXXX,phoneXXX,faxXXX,homepageXXX"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Suppliers_whenExecuteFindSuppliersByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var suppliersEntity = createSuppliers("");
	    for (int i = 0; i < 9; i++) {
	    	createSuppliers(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "supplierId|eq|"+suppliersEntity.getSupplierId()+
							";:')"+"||"+"companyName|eq|"+suppliersEntity.getCompanyName()+
							";:')"+"||"+"contactName|eq|"+suppliersEntity.getContactName()+
							";:')"+"||"+"contactTitle|eq|"+suppliersEntity.getContactTitle()+
							";:')"+"||"+"address|eq|"+suppliersEntity.getAddress()+
							";:')"+"||"+"city|eq|"+suppliersEntity.getCity()+
							";:')"+"||"+"region|eq|"+suppliersEntity.getRegion()+
							";:')"+"||"+"postalCode|eq|"+suppliersEntity.getPostalCode()+
							";:')"+"||"+"country|eq|"+suppliersEntity.getCountry()+
							";:')"+"||"+"phone|eq|"+suppliersEntity.getPhone()+
							";:')"+"||"+"fax|eq|"+suppliersEntity.getFax()+
							";:')"+"||"+"homepage|eq|"+suppliersEntity.getHomepage()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","supplierId,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax,homepage"
	        )
	        .get(SUPPLIERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
