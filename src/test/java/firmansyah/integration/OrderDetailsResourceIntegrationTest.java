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
import firmansyah.application.web.model.request.NewOrderDetailsRequest;
import firmansyah.application.web.model.request.UpdateOrderDetailsRequest;
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
public class OrderDetailsResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String ORDERDETAILS_RESOURCE_PATH = API_PREFIX + "/firmansyah/orderDetails";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewOrderDetails_whenExecuteCreateEndpoint_shouldReturnCreatedOrderDetails201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewOrderDetailsRequest orderDetails = new NewOrderDetailsRequest();
			orderDetails.setUnitPrice(1.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(1.0);
			final var ordersOrderIdEntity= createOrders("");
			orderDetails.setOrderId( ordersOrderIdEntity.getOrderId());
			final var productsProductIdEntity= createProducts("");
			orderDetails.setProductId( productsProductIdEntity.getProductId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(orderDetails)).when()
		       .post(ORDERDETAILS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"orderDetails.ordersOrderIdResponse.orderId",
					 Matchers.notNullValue(),
					"orderDetails.ordersOrderIdResponse.orderId",
					 is( ordersOrderIdEntity.getOrderId()),
					"orderDetails.productsProductIdResponse.productId",
					 Matchers.notNullValue(),
					"orderDetails.productsProductIdResponse.productId",
					 is( productsProductIdEntity.getProductId()),
					"orderDetails.unitPrice",
					 Matchers.notNullValue(),
					"orderDetails.unitPrice",
					 is(orderDetails.getUnitPrice()),
					"orderDetails.quantity",
					 Matchers.notNullValue(),
					"orderDetails.quantity",
					 is(orderDetails.getQuantity()),
					"orderDetails.discount",
					 Matchers.notNullValue(),
					"orderDetails.discount",
					 is(orderDetails.getDiscount())
					);
  	}
  
  	@Test
  	public void givenANewOrderDetailsWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewOrderDetailsRequest orderDetails = new NewOrderDetailsRequest();
			orderDetails.setUnitPrice(1.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(2.0);
			final var ordersOrderIdEntity= createOrders("");
			orderDetails.setOrderId( ordersOrderIdEntity.getOrderId());
			final var productsProductIdEntity= createProducts("");
			orderDetails.setProductId( productsProductIdEntity.getProductId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.post(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidOrderDetails_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewOrderDetailsRequest orderDetails = new NewOrderDetailsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.when()
        	.post(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(5),
            	"errors.body",
            	hasItems(
						"orderId of OrderDetails must not be blank",
						"productId of OrderDetails must not be blank",
						"unitPrice of OrderDetails must not be blank",
						"quantity of OrderDetails must not be blank",
						"discount of OrderDetails must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentOrderDetails_whenExecuteCreateEndpoint_shouldReturnConflictOrderDetailsRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var orderDetailsEntity = createOrderDetails("");
		NewOrderDetailsRequest orderDetails = new NewOrderDetailsRequest();
			orderDetails.setOrderId(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId());
			orderDetails.setProductId(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId());
			orderDetails.setUnitPrice(2.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(2.0);
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.post(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("orderdetails already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentOrderDetails_whenExecuteUpdateEndpoint_shouldReturnUpdatedOrderDetails()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var orderDetailsEntity = createOrderDetails("");
		UpdateOrderDetailsRequest orderDetails = new UpdateOrderDetailsRequest();
			orderDetails.setOrderId(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId());
			orderDetails.setProductId(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId());
			orderDetails.setUnitPrice(2.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(2.0);
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.put(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"orderDetails.ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails.ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails.productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails.productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails.unitPrice",
				 Matchers.notNullValue(),
				"orderDetails.unitPrice",
				 is(orderDetails.getUnitPrice()),
				"orderDetails.quantity",
				 Matchers.notNullValue(),
				"orderDetails.quantity",
				 is(orderDetails.getQuantity()),
				"orderDetails.discount",
				 Matchers.notNullValue(),
				"orderDetails.discount",
				 is(orderDetails.getDiscount())
        		);
        	
			Assertions.assertEquals(orderDetails.getOrderId(),
			 orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId());Assertions.assertEquals(orderDetails.getProductId(),
			 orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId());Assertions.assertEquals(orderDetails.getUnitPrice(),
			 orderDetailsEntity.getUnitPrice());Assertions.assertEquals(orderDetails.getQuantity(),
			 orderDetailsEntity.getQuantity());Assertions.assertEquals(orderDetails.getDiscount(),
			 orderDetailsEntity.getDiscount());
  	}
  
  	@Test
  	public void givenAExistentOrderDetailsWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var orderDetailsEntity = createOrderDetails("");
		UpdateOrderDetailsRequest orderDetails = new UpdateOrderDetailsRequest();
			orderDetails.setOrderId(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId());
			orderDetails.setProductId(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId());
			orderDetails.setUnitPrice(0.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(0.0);
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.put(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentOrderDetails_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateOrderDetailsRequest orderDetails = new UpdateOrderDetailsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.when()
        	.put(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(5+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"orderId of OrderDetails must not be blank",
						"productId of OrderDetails must not be blank",
						"unitPrice of OrderDetails must not be blank",
						"quantity of OrderDetails must not be blank",
						"discount of OrderDetails must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentOrderDetails_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateOrderDetailsRequest orderDetails = new UpdateOrderDetailsRequest();
			orderDetails.setUnitPrice(0.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(0.0);
			final var ordersOrderIdEntity= createOrders("");
			orderDetails.setOrderId( ordersOrderIdEntity.getOrderId());
			final var productsProductIdEntity= createProducts("");
			orderDetails.setProductId( productsProductIdEntity.getProductId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.when()
        	.put(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("orderDetails not found"));
  	}
  
  	@Test
  	public void givenANewOrderDetailsWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewOrderDetailsRequest orderDetails = new NewOrderDetailsRequest();
			orderDetails.setUnitPrice(0.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(0.0);
			orderDetails.setOrderId(2);
			orderDetails.setProductId(2);
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orderDetails))
        	.when()
        	.post(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("orders not found"),hasItems("products not found")));
  	}
  
   	@Test
   	public void givenAExistentOrderDetailsWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var orderDetailsEntity = createOrderDetails("");
		UpdateOrderDetailsRequest orderDetails = new UpdateOrderDetailsRequest();
			orderDetails.setOrderId(2);
			orderDetails.setProductId(2);
			orderDetails.setUnitPrice(3.0);
			orderDetails.setQuantity(2);
			orderDetails.setDiscount(3.0);
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(orderDetails))
         	.when()
         	.put(ORDERDETAILS_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("orderDetails not found"),hasItems("orders not found"),hasItems("products not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentOrderDetails_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("orderId", 2)
			.queryParam("productId", 2)
 	        .delete(ORDERDETAILS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentOrderDetails_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var orderDetailsEntity = createOrderDetails("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("orderId", orderDetailsEntity.getOrdersOrderId().getOrderId())
		.queryParam("productId", orderDetailsEntity.getProductsProductId().getProductId())
 	        .delete(ORDERDETAILS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findOrderDetailsEntityByPK(
		 orderDetailsEntity.getOrdersOrderId().getOrderId()
		,		     
		 orderDetailsEntity.getProductsProductId().getProductId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentOrderDetailsWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var orderDetailsEntity = createOrderDetails("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		.queryParam("orderId", orderDetailsEntity.getOrdersOrderId().getOrderId())
		.queryParam("productId", orderDetailsEntity.getProductsProductId().getProductId())
        	.delete(ORDERDETAILS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentOrderDetails_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var orderDetailsEntity = createOrderDetails("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		.queryParam("orderId", orderDetailsEntity.getOrdersOrderId().getOrderId())
		.queryParam("productId", orderDetailsEntity.getProductsProductId().getProductId())
			.get(ORDERDETAILS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"orderDetails.ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails.ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails.productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails.productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails.unitPrice",
				 Matchers.notNullValue(),
				"orderDetails.unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails.quantity",
				 Matchers.notNullValue(),
				"orderDetails.quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails.discount",
				 Matchers.notNullValue(),
				"orderDetails.discount",
				 is(orderDetailsEntity.getDiscount())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentOrderDetails_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var orderDetailsEntity = createOrderDetails("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("orderId", 2)
			.queryParam("productId", 2)
			.get(ORDERDETAILS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentOrderDetailsWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var orderDetailsEntity = createOrderDetails("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		.queryParam("orderId", orderDetailsEntity.getOrdersOrderId().getOrderId())
		.queryParam("productId", orderDetailsEntity.getProductsProductId().getProductId())
			.get(ORDERDETAILS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"orderDetails.ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails.ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails.productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails.productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails.unitPrice",
				 Matchers.notNullValue(),
				"orderDetails.unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails.quantity",
				 Matchers.notNullValue(),
				"orderDetails.quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails.discount",
				 Matchers.notNullValue(),
				"orderDetails.discount",
				 is(orderDetailsEntity.getDiscount())
				);
	}
   
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset0AndLimit5_shouldReturnListOf5OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orderDetails[0]",
					 hasKey("ordersOrderIdResponse"),
					"orderDetails[0]",
					 hasKey("productsProductIdResponse"),
					"orderDetails[0]",
					 hasKey("unitPrice"),
					"orderDetails[0]",
					 hasKey("quantity"),
					"orderDetails[0]",
					 hasKey("discount") ,
					"orderDetailsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset0AndLimit10_shouldReturnListOf10OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orderDetails[0]",
					 hasKey("ordersOrderIdResponse"),
					"orderDetails[0]",
					 hasKey("productsProductIdResponse"),
					"orderDetails[0]",
					 hasKey("unitPrice"),
					"orderDetails[0]",
					 hasKey("quantity"),
					"orderDetails[0]",
					 hasKey("discount") ,
					"orderDetailsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset0AndLimit15_shouldReturnListOf10OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orderDetails[0]",
					 hasKey("ordersOrderIdResponse"),
					"orderDetails[0]",
					 hasKey("productsProductIdResponse"),
					"orderDetails[0]",
					 hasKey("unitPrice"),
					"orderDetails[0]",
					 hasKey("quantity"),
					"orderDetails[0]",
					 hasKey("discount") ,
					"orderDetailsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset10AndLimit5_shouldReturnListOf5OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orderDetails[0]",
					 hasKey("ordersOrderIdResponse"),
					"orderDetails[0]",
					 hasKey("productsProductIdResponse"),
					"orderDetails[0]",
					 hasKey("unitPrice"),
					"orderDetails[0]",
					 hasKey("quantity"),
					"orderDetails[0]",
					 hasKey("discount") ,
					"orderDetailsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset20AndLimit5_shouldReturnListOf0OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"orderDetailsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset10AndLimit10_shouldReturnListOf0OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"orderDetailsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilterWithOffset10AndLimit115_shouldReturnListOf0OrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"orderDetailsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 is(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 is(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|neq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|neq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|neq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|neq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|neq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 not(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 not(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 not(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 not(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 not(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|like|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|like|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|like|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|like|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|like|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 is(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|nlike|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|nlike|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|nlike|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|nlike|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|nlike|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 not(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 not(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 not(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 not(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 not(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,productId,unitPrice,quantity,discount"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 is(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredOrderDetails() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-orderId,-productId,-unitPrice,-quantity,-discount"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 Matchers.notNullValue(),
				"orderDetails[0].ordersOrderIdResponse.orderId",
				 is(orderDetailsEntity.getPrimaryKey().getOrdersOrderId().getOrderId()),
				"orderDetails[0].productsProductIdResponse.productId",
				 Matchers.notNullValue(),
				"orderDetails[0].productsProductIdResponse.productId",
				 is(orderDetailsEntity.getPrimaryKey().getProductsProductId().getProductId()),
				"orderDetails[0].unitPrice",
				 Matchers.notNullValue(),
				"orderDetails[0].unitPrice",
				 is(orderDetailsEntity.getUnitPrice()),
				"orderDetails[0].quantity",
				 Matchers.notNullValue(),
				"orderDetails[0].quantity",
				 is(orderDetailsEntity.getQuantity()),
				"orderDetails[0].discount",
				 Matchers.notNullValue(),
				"orderDetails[0].discount",
				 is(orderDetailsEntity.getDiscount())
				);
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderIdXXX|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productIdXXX|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPriceXXX|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantityXXX|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discountXXX|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,productId,unitPrice,quantity,discount"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eqXXX|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eqXXX|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eqXXX|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eqXXX|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eqXXX|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,productId,unitPrice,quantity,discount"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","orderId,productId,unitPrice,quantity,discount"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							""+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							""+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							""+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							""+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderIdXXX,productIdXXX,unitPriceXXX,quantityXXX,discountXXX"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10OrderDetails_whenExecuteFindOrderDetailsByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var orderDetailsEntity = createOrderDetails("");
	    for (int i = 0; i < 9; i++) {
	    	createOrderDetails(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+orderDetailsEntity.getOrdersOrderId().getOrderId()+
							";:')"+"||"+"productId|eq|"+orderDetailsEntity.getProductsProductId().getProductId()+
							";:')"+"||"+"unitPrice|eq|"+orderDetailsEntity.getUnitPrice()+
							";:')"+"||"+"quantity|eq|"+orderDetailsEntity.getQuantity()+
							";:')"+"||"+"discount|eq|"+orderDetailsEntity.getDiscount()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,productId,unitPrice,quantity,discount"
	        )
	        .get(ORDERDETAILS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
