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
import firmansyah.application.web.model.request.NewOrdersRequest;
import firmansyah.application.web.model.request.UpdateOrdersRequest;
import firmansyah.utils.ResourcesIntegrationTest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class OrdersResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String ORDERS_RESOURCE_PATH = API_PREFIX + "/firmansyah/orders";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewOrders_whenExecuteCreateEndpoint_shouldReturnCreatedOrders201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewOrdersRequest orders = new NewOrdersRequest();
			orders.setOrderId(2);
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			orders.setFreight(4.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			final var employeesEmployeeIdEntity= createEmployees("");
			orders.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			final var shippersShipViaEntity= createShippers("");
			orders.setShipVia( shippersShipViaEntity.getShipperId());
			final var customersCustomerIdEntity= createCustomers("");
			orders.setCustomerId( customersCustomerIdEntity.getCustomerId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(orders)).when()
		       .post(ORDERS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"orders.orderId",
					 Matchers.notNullValue(),
					"orders.orderId",
					 is(orders.getOrderId()),
					"orders.customersCustomerIdResponse.customerId",
					 Matchers.notNullValue(),
					"orders.customersCustomerIdResponse.customerId",
					 is( customersCustomerIdEntity.getCustomerId()),
					"orders.employeesEmployeeIdResponse.employeeId",
					 Matchers.notNullValue(),
					"orders.employeesEmployeeIdResponse.employeeId",
					 is( employeesEmployeeIdEntity.getEmployeeId()),
					"orders.orderDate",
					 Matchers.notNullValue(),
					"orders.orderDate",
					 is(orders.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"orders.requiredDate",
					 Matchers.notNullValue(),
					"orders.requiredDate",
					 is(orders.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"orders.shippedDate",
					 Matchers.notNullValue(),
					"orders.shippedDate",
					 is(orders.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"orders.shippersShipViaResponse.shipperId",
					 Matchers.notNullValue(),
					"orders.shippersShipViaResponse.shipperId",
					 is( shippersShipViaEntity.getShipperId()),
					"orders.freight",
					 Matchers.notNullValue(),
					"orders.freight",
					 is(orders.getFreight()),
					"orders.shipName",
					 Matchers.notNullValue(),
					"orders.shipName",
					 is(orders.getShipName()),
					"orders.shipAddress",
					 Matchers.notNullValue(),
					"orders.shipAddress",
					 is(orders.getShipAddress()),
					"orders.shipCity",
					 Matchers.notNullValue(),
					"orders.shipCity",
					 is(orders.getShipCity()),
					"orders.shipRegion",
					 Matchers.notNullValue(),
					"orders.shipRegion",
					 is(orders.getShipRegion()),
					"orders.shipPostalCode",
					 Matchers.notNullValue(),
					"orders.shipPostalCode",
					 is(orders.getShipPostalCode()),
					"orders.shipCountry",
					 Matchers.notNullValue(),
					"orders.shipCountry",
					 is(orders.getShipCountry())
					);
  	}
  
  	@Test
  	public void givenANewOrdersWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewOrdersRequest orders = new NewOrdersRequest();
			orders.setOrderId(2);
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			orders.setFreight(1.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			final var employeesEmployeeIdEntity= createEmployees("");
			orders.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			final var shippersShipViaEntity= createShippers("");
			orders.setShipVia( shippersShipViaEntity.getShipperId());
			final var customersCustomerIdEntity= createCustomers("");
			orders.setCustomerId( customersCustomerIdEntity.getCustomerId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(orders))
        	.post(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidOrders_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewOrdersRequest orders = new NewOrdersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orders))
        	.when()
        	.post(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1),
            	"errors.body",
            	hasItems(
						"orderId of Orders must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentOrders_whenExecuteCreateEndpoint_shouldReturnConflictOrdersRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var ordersEntity = createOrders("");
		NewOrdersRequest orders = new NewOrdersRequest();
			orders.setOrderId(ordersEntity.getOrderId());
			final var customersCustomerIdEntity= createCustomers("");
			orders.setCustomerId( customersCustomerIdEntity.getCustomerId());
			final var employeesEmployeeIdEntity= createEmployees("");
			orders.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			final var shippersShipViaEntity= createShippers("");
			orders.setShipVia( shippersShipViaEntity.getShipperId());
			orders.setFreight(2.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orders))
        	.post(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("orders already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentOrders_whenExecuteUpdateEndpoint_shouldReturnUpdatedOrders()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var ordersEntity = createOrders("");
		UpdateOrdersRequest orders = new UpdateOrdersRequest();
			orders.setOrderId(ordersEntity.getOrderId());
			final var customersCustomerIdEntity= createCustomers("");
			orders.setCustomerId( customersCustomerIdEntity.getCustomerId());
			final var employeesEmployeeIdEntity= createEmployees("");
			orders.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			final var shippersShipViaEntity= createShippers("");
			orders.setShipVia( shippersShipViaEntity.getShipperId());
			orders.setFreight(5.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orders))
        	.put(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"orders.orderId",
				 Matchers.notNullValue(),
				"orders.orderId",
				 is(orders.getOrderId()),
				"orders.customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders.customersCustomerIdResponse.customerId",
				 is(customersCustomerIdEntity.getCustomerId()),
				"orders.employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders.employeesEmployeeIdResponse.employeeId",
				 is(employeesEmployeeIdEntity.getEmployeeId()),
				"orders.orderDate",
				 Matchers.notNullValue(),
				"orders.orderDate",
				 is(orders.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.requiredDate",
				 Matchers.notNullValue(),
				"orders.requiredDate",
				 is(orders.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.shippedDate",
				 Matchers.notNullValue(),
				"orders.shippedDate",
				 is(orders.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders.shippersShipViaResponse.shipperId",
				 is(shippersShipViaEntity.getShipperId()),
				"orders.freight",
				 Matchers.notNullValue(),
				"orders.freight",
				 is(orders.getFreight()),
				"orders.shipName",
				 Matchers.notNullValue(),
				"orders.shipName",
				 is(orders.getShipName()),
				"orders.shipAddress",
				 Matchers.notNullValue(),
				"orders.shipAddress",
				 is(orders.getShipAddress()),
				"orders.shipCity",
				 Matchers.notNullValue(),
				"orders.shipCity",
				 is(orders.getShipCity()),
				"orders.shipRegion",
				 Matchers.notNullValue(),
				"orders.shipRegion",
				 is(orders.getShipRegion()),
				"orders.shipPostalCode",
				 Matchers.notNullValue(),
				"orders.shipPostalCode",
				 is(orders.getShipPostalCode()),
				"orders.shipCountry",
				 Matchers.notNullValue(),
				"orders.shipCountry",
				 is(orders.getShipCountry())
        		);
        	
			Assertions.assertEquals(orders.getOrderId(),
			 ordersEntity.getOrderId());Assertions.assertEquals(orders.getCustomerId(),
			 customersCustomerIdEntity.getCustomerId());Assertions.assertEquals(orders.getEmployeeId(),
			 employeesEmployeeIdEntity.getEmployeeId());Assertions.assertEquals(orders.getShipVia(),
			 shippersShipViaEntity.getShipperId());Assertions.assertEquals(orders.getFreight(),
			 ordersEntity.getFreight());Assertions.assertEquals(orders.getShipName(),
			 ordersEntity.getShipName());Assertions.assertEquals(orders.getShipAddress(),
			 ordersEntity.getShipAddress());Assertions.assertEquals(orders.getShipCity(),
			 ordersEntity.getShipCity());Assertions.assertEquals(orders.getShipRegion(),
			 ordersEntity.getShipRegion());Assertions.assertEquals(orders.getShipPostalCode(),
			 ordersEntity.getShipPostalCode());Assertions.assertEquals(orders.getShipCountry(),
			 ordersEntity.getShipCountry());
  	}
  
  	@Test
  	public void givenAExistentOrdersWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var ordersEntity = createOrders("");
		UpdateOrdersRequest orders = new UpdateOrdersRequest();
			orders.setOrderId(ordersEntity.getOrderId());
			final var customersCustomerIdEntity= createCustomers("");
			orders.setCustomerId( customersCustomerIdEntity.getCustomerId());
			final var employeesEmployeeIdEntity= createEmployees("");
			orders.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			final var shippersShipViaEntity= createShippers("");
			orders.setShipVia( shippersShipViaEntity.getShipperId());
			orders.setFreight(3.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(orders))
        	.put(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentOrders_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateOrdersRequest orders = new UpdateOrdersRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orders))
        	.when()
        	.put(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(1+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"orderId of Orders must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentOrders_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateOrdersRequest orders = new UpdateOrdersRequest();
			orders.setOrderId(2);
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			orders.setFreight(6.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			final var employeesEmployeeIdEntity= createEmployees("");
			orders.setEmployeeId( employeesEmployeeIdEntity.getEmployeeId());
			final var shippersShipViaEntity= createShippers("");
			orders.setShipVia( shippersShipViaEntity.getShipperId());
			final var customersCustomerIdEntity= createCustomers("");
			orders.setCustomerId( customersCustomerIdEntity.getCustomerId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orders))
        	.when()
        	.put(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("orders not found"));
  	}
  
  	@Test
  	public void givenANewOrdersWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewOrdersRequest orders = new NewOrdersRequest();
			orders.setOrderId(2);
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			orders.setFreight(6.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			orders.setEmployeeId(2);
			orders.setShipVia(2);
			orders.setCustomerId("customerId");
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(orders))
        	.when()
        	.post(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("employees not found"),hasItems("shippers not found"),hasItems("customers not found")));
  	}
  
   	@Test
   	public void givenAExistentOrdersWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var ordersEntity = createOrders("");
		UpdateOrdersRequest orders = new UpdateOrdersRequest();
			orders.setOrderId(ordersEntity.getOrderId());
			orders.setCustomerId("customerId");
			orders.setEmployeeId(2);
			orders.setOrderDate(LocalDateTime.now());
			orders.setRequiredDate(LocalDateTime.now());
			orders.setShippedDate(LocalDateTime.now());
			orders.setShipVia(2);
			orders.setFreight(1.0);
			orders.setShipName("shipName");
			orders.setShipAddress("shipAddress");
			orders.setShipCity("shipCity");
			orders.setShipRegion("shipRegion");
			orders.setShipPostalCode("shipPostalCode");
			orders.setShipCountry("shipCountry");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(orders))
         	.when()
         	.put(ORDERS_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("orders not found"),hasItems("employees not found"),hasItems("shippers not found"),hasItems("customers not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentOrders_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("orderId", 2)
 	        .delete(ORDERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentOrders_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var ordersEntity = createOrders("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("orderId", ordersEntity.getOrderId())
 	        .delete(ORDERS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findOrdersEntityByPK(
	 ordersEntity.getOrderId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentOrdersWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var ordersEntity = createOrders("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("orderId", ordersEntity.getOrderId())
        	.delete(ORDERS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentOrders_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var ordersEntity = createOrders("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("orderId", ordersEntity.getOrderId())
			.get(ORDERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"orders.orderId",
				 Matchers.notNullValue(),
				"orders.orderId",
				 is(ordersEntity.getOrderId()),
				"orders.customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders.customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders.employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders.employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders.orderDate",
				 Matchers.notNullValue(),
				"orders.orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.requiredDate",
				 Matchers.notNullValue(),
				"orders.requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.shippedDate",
				 Matchers.notNullValue(),
				"orders.shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders.shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders.freight",
				 Matchers.notNullValue(),
				"orders.freight",
				 is(ordersEntity.getFreight()),
				"orders.shipName",
				 Matchers.notNullValue(),
				"orders.shipName",
				 is(ordersEntity.getShipName()),
				"orders.shipAddress",
				 Matchers.notNullValue(),
				"orders.shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders.shipCity",
				 Matchers.notNullValue(),
				"orders.shipCity",
				 is(ordersEntity.getShipCity()),
				"orders.shipRegion",
				 Matchers.notNullValue(),
				"orders.shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders.shipPostalCode",
				 Matchers.notNullValue(),
				"orders.shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders.shipCountry",
				 Matchers.notNullValue(),
				"orders.shipCountry",
				 is(ordersEntity.getShipCountry())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentOrders_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var ordersEntity = createOrders("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("orderId", 2)
			.get(ORDERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentOrdersWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var ordersEntity = createOrders("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("orderId", ordersEntity.getOrderId())
			.get(ORDERS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"orders.orderId",
				 Matchers.notNullValue(),
				"orders.orderId",
				 is(ordersEntity.getOrderId()),
				"orders.customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders.customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders.employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders.employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders.orderDate",
				 Matchers.notNullValue(),
				"orders.orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.requiredDate",
				 Matchers.notNullValue(),
				"orders.requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.shippedDate",
				 Matchers.notNullValue(),
				"orders.shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders.shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders.shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders.freight",
				 Matchers.notNullValue(),
				"orders.freight",
				 is(ordersEntity.getFreight()),
				"orders.shipName",
				 Matchers.notNullValue(),
				"orders.shipName",
				 is(ordersEntity.getShipName()),
				"orders.shipAddress",
				 Matchers.notNullValue(),
				"orders.shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders.shipCity",
				 Matchers.notNullValue(),
				"orders.shipCity",
				 is(ordersEntity.getShipCity()),
				"orders.shipRegion",
				 Matchers.notNullValue(),
				"orders.shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders.shipPostalCode",
				 Matchers.notNullValue(),
				"orders.shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders.shipCountry",
				 Matchers.notNullValue(),
				"orders.shipCountry",
				 is(ordersEntity.getShipCountry())
				);
	}
   
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilterWithOffset0AndLimit5_shouldReturnListOf5Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orders[0]",
					 hasKey("orderId"),
					"orders[0]",
					 hasKey("customersCustomerIdResponse"),
					"orders[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"orders[0]",
					 hasKey("orderDate"),
					"orders[0]",
					 hasKey("requiredDate"),
					"orders[0]",
					 hasKey("shippedDate"),
					"orders[0]",
					 hasKey("shippersShipViaResponse"),
					"orders[0]",
					 hasKey("freight"),
					"orders[0]",
					 hasKey("shipName"),
					"orders[0]",
					 hasKey("shipAddress"),
					"orders[0]",
					 hasKey("shipCity"),
					"orders[0]",
					 hasKey("shipRegion"),
					"orders[0]",
					 hasKey("shipPostalCode"),
					"orders[0]",
					 hasKey("shipCountry") ,
					"ordersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilterWithOffset0AndLimit10_shouldReturnListOf10Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orders[0]",
					 hasKey("orderId"),
					"orders[0]",
					 hasKey("customersCustomerIdResponse"),
					"orders[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"orders[0]",
					 hasKey("orderDate"),
					"orders[0]",
					 hasKey("requiredDate"),
					"orders[0]",
					 hasKey("shippedDate"),
					"orders[0]",
					 hasKey("shippersShipViaResponse"),
					"orders[0]",
					 hasKey("freight"),
					"orders[0]",
					 hasKey("shipName"),
					"orders[0]",
					 hasKey("shipAddress"),
					"orders[0]",
					 hasKey("shipCity"),
					"orders[0]",
					 hasKey("shipRegion"),
					"orders[0]",
					 hasKey("shipPostalCode"),
					"orders[0]",
					 hasKey("shipCountry") ,
					"ordersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilterWithOffset0AndLimit15_shouldReturnListOf10Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orders[0]",
					 hasKey("orderId"),
					"orders[0]",
					 hasKey("customersCustomerIdResponse"),
					"orders[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"orders[0]",
					 hasKey("orderDate"),
					"orders[0]",
					 hasKey("requiredDate"),
					"orders[0]",
					 hasKey("shippedDate"),
					"orders[0]",
					 hasKey("shippersShipViaResponse"),
					"orders[0]",
					 hasKey("freight"),
					"orders[0]",
					 hasKey("shipName"),
					"orders[0]",
					 hasKey("shipAddress"),
					"orders[0]",
					 hasKey("shipCity"),
					"orders[0]",
					 hasKey("shipRegion"),
					"orders[0]",
					 hasKey("shipPostalCode"),
					"orders[0]",
					 hasKey("shipCountry") ,
					"ordersCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Orders_whenExecuteFindOrdersByFilterWithOffset10AndLimit5_shouldReturnListOf5Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"orders[0]",
					 hasKey("orderId"),
					"orders[0]",
					 hasKey("customersCustomerIdResponse"),
					"orders[0]",
					 hasKey("employeesEmployeeIdResponse"),
					"orders[0]",
					 hasKey("orderDate"),
					"orders[0]",
					 hasKey("requiredDate"),
					"orders[0]",
					 hasKey("shippedDate"),
					"orders[0]",
					 hasKey("shippersShipViaResponse"),
					"orders[0]",
					 hasKey("freight"),
					"orders[0]",
					 hasKey("shipName"),
					"orders[0]",
					 hasKey("shipAddress"),
					"orders[0]",
					 hasKey("shipCity"),
					"orders[0]",
					 hasKey("shipRegion"),
					"orders[0]",
					 hasKey("shipPostalCode"),
					"orders[0]",
					 hasKey("shipCountry") ,
					"ordersCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilterWithOffset20AndLimit5_shouldReturnListOf0Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"ordersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilterWithOffset10AndLimit10_shouldReturnListOf0Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"ordersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilterWithOffset10AndLimit115_shouldReturnListOf0Orders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createOrders(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"ordersCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 is(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 is(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 is(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 is(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 is(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 is(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 is(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 is(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 is(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 is(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|neq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|neq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|neq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|neq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|neq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|neq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|neq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|neq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|neq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|neq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|neq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|neq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|neq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|neq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 not(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 not(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 not(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 not(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 not(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 not(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 not(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 not(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 not(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 not(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 not(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 not(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 not(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 not(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|like|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|like|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|like|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|like|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|like|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|like|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|like|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|like|"+ordersEntity.getFreight()+
							""+"||"+"shipName|like|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|like|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|like|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|like|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|like|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|like|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 is(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 is(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 is(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 is(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 is(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|nlike|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|nlike|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|nlike|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"shipVia|nlike|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|nlike|"+ordersEntity.getFreight()+
							""+"||"+"shipName|nlike|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|nlike|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|nlike|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|nlike|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|nlike|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|nlike|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 not(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 not(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 not(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 not(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 not(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 not(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 not(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 not(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 not(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 not(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 not(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 not(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 not(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 not(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,customerId,employeeId,orderDate,requiredDate,shippedDate,shipVia,freight,shipName,shipAddress,shipCity,shipRegion,shipPostalCode,shipCountry"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 is(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 is(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 is(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 is(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 is(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredOrders() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-orderId,-customerId,-employeeId,-orderDate,-requiredDate,-shippedDate,-shipVia,-freight,-shipName,-shipAddress,-shipCity,-shipRegion,-shipPostalCode,-shipCountry"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"orders[0].orderId",
				 Matchers.notNullValue(),
				"orders[0].orderId",
				 is(ordersEntity.getOrderId()),
				"orders[0].customersCustomerIdResponse.customerId",
				 Matchers.notNullValue(),
				"orders[0].customersCustomerIdResponse.customerId",
				 is(ordersEntity.getCustomersCustomerId().getCustomerId()),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 Matchers.notNullValue(),
				"orders[0].employeesEmployeeIdResponse.employeeId",
				 is(ordersEntity.getEmployeesEmployeeId().getEmployeeId()),
				"orders[0].orderDate",
				 Matchers.notNullValue(),
				"orders[0].orderDate",
				 is(ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].requiredDate",
				 Matchers.notNullValue(),
				"orders[0].requiredDate",
				 is(ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippedDate",
				 Matchers.notNullValue(),
				"orders[0].shippedDate",
				 is(ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"orders[0].shippersShipViaResponse.shipperId",
				 Matchers.notNullValue(),
				"orders[0].shippersShipViaResponse.shipperId",
				 is(ordersEntity.getShippersShipVia().getShipperId()),
				"orders[0].freight",
				 Matchers.notNullValue(),
				"orders[0].freight",
				 is(ordersEntity.getFreight()),
				"orders[0].shipName",
				 Matchers.notNullValue(),
				"orders[0].shipName",
				 is(ordersEntity.getShipName()),
				"orders[0].shipAddress",
				 Matchers.notNullValue(),
				"orders[0].shipAddress",
				 is(ordersEntity.getShipAddress()),
				"orders[0].shipCity",
				 Matchers.notNullValue(),
				"orders[0].shipCity",
				 is(ordersEntity.getShipCity()),
				"orders[0].shipRegion",
				 Matchers.notNullValue(),
				"orders[0].shipRegion",
				 is(ordersEntity.getShipRegion()),
				"orders[0].shipPostalCode",
				 Matchers.notNullValue(),
				"orders[0].shipPostalCode",
				 is(ordersEntity.getShipPostalCode()),
				"orders[0].shipCountry",
				 Matchers.notNullValue(),
				"orders[0].shipCountry",
				 is(ordersEntity.getShipCountry())
				);
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderIdXXX|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerIdXXX|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeIdXXX|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDateXXX|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDateXXX|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDateXXX|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipViaXXX|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freightXXX|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipNameXXX|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddressXXX|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCityXXX|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegionXXX|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCodeXXX|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountryXXX|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,customerId,employeeId,orderDate,requiredDate,shippedDate,shipVia,freight,shipName,shipAddress,shipCity,shipRegion,shipPostalCode,shipCountry"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eqXXX|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eqXXX|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eqXXX|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eqXXX|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eqXXX|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eqXXX|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eqXXX|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eqXXX|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eqXXX|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eqXXX|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eqXXX|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eqXXX|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eqXXX|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eqXXX|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,customerId,employeeId,orderDate,requiredDate,shippedDate,shipVia,freight,shipName,shipAddress,shipCity,shipRegion,shipPostalCode,shipCountry"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","orderId,customerId,employeeId,orderDate,requiredDate,shippedDate,shipVia,freight,shipName,shipAddress,shipCity,shipRegion,shipPostalCode,shipCountry"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							""+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							""+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							""+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							""+"||"+"freight|eq|"+ordersEntity.getFreight()+
							""+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							""+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							""+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							""+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							""+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							""+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderIdXXX,customerIdXXX,employeeIdXXX,orderDateXXX,requiredDateXXX,shippedDateXXX,shipViaXXX,freightXXX,shipNameXXX,shipAddressXXX,shipCityXXX,shipRegionXXX,shipPostalCodeXXX,shipCountryXXX"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Orders_whenExecuteFindOrdersByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var ordersEntity = createOrders("");
	    for (int i = 0; i < 9; i++) {
	    	createOrders(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "orderId|eq|"+ordersEntity.getOrderId()+
							";:')"+"||"+"customerId|eq|"+ordersEntity.getCustomersCustomerId().getCustomerId()+
							";:')"+"||"+"employeeId|eq|"+ordersEntity.getEmployeesEmployeeId().getEmployeeId()+
							";:')"+"||"+"orderDate|eq|"+ordersEntity.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"requiredDate|eq|"+ordersEntity.getRequiredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"shippedDate|eq|"+ordersEntity.getShippedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"shipVia|eq|"+ordersEntity.getShippersShipVia().getShipperId()+
							";:')"+"||"+"freight|eq|"+ordersEntity.getFreight()+
							";:')"+"||"+"shipName|eq|"+ordersEntity.getShipName()+
							";:')"+"||"+"shipAddress|eq|"+ordersEntity.getShipAddress()+
							";:')"+"||"+"shipCity|eq|"+ordersEntity.getShipCity()+
							";:')"+"||"+"shipRegion|eq|"+ordersEntity.getShipRegion()+
							";:')"+"||"+"shipPostalCode|eq|"+ordersEntity.getShipPostalCode()+
							";:')"+"||"+"shipCountry|eq|"+ordersEntity.getShipCountry()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","orderId,customerId,employeeId,orderDate,requiredDate,shippedDate,shipVia,freight,shipName,shipAddress,shipCity,shipRegion,shipPostalCode,shipCountry"
	        )
	        .get(ORDERS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
