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
import firmansyah.application.web.model.request.NewProductsRequest;
import firmansyah.application.web.model.request.UpdateProductsRequest;
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
public class ProductsResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String PRODUCTS_RESOURCE_PATH = API_PREFIX + "/firmansyah/products";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewProducts_whenExecuteCreateEndpoint_shouldReturnCreatedProducts201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewProductsRequest products = new NewProductsRequest();
			products.setProductId(2);
			products.setProductName("productName");
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(5.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(7);
			final var categoriesCategoryIdEntity= createCategories("");
			products.setCategoryId( categoriesCategoryIdEntity.getCategoryId());
			final var suppliersSupplierIdEntity= createSuppliers("");
			products.setSupplierId( suppliersSupplierIdEntity.getSupplierId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(products)).when()
		       .post(PRODUCTS_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"products.productId",
					 Matchers.notNullValue(),
					"products.productId",
					 is(products.getProductId()),
					"products.productName",
					 Matchers.notNullValue(),
					"products.productName",
					 is(products.getProductName()),
					"products.suppliersSupplierIdResponse.supplierId",
					 Matchers.notNullValue(),
					"products.suppliersSupplierIdResponse.supplierId",
					 is( suppliersSupplierIdEntity.getSupplierId()),
					"products.categoriesCategoryIdResponse.categoryId",
					 Matchers.notNullValue(),
					"products.categoriesCategoryIdResponse.categoryId",
					 is( categoriesCategoryIdEntity.getCategoryId()),
					"products.quantityPerUnit",
					 Matchers.notNullValue(),
					"products.quantityPerUnit",
					 is(products.getQuantityPerUnit()),
					"products.unitPrice",
					 Matchers.notNullValue(),
					"products.unitPrice",
					 is(products.getUnitPrice()),
					"products.unitsInStock",
					 Matchers.notNullValue(),
					"products.unitsInStock",
					 is(products.getUnitsInStock()),
					"products.unitsOnOrder",
					 Matchers.notNullValue(),
					"products.unitsOnOrder",
					 is(products.getUnitsOnOrder()),
					"products.reorderLevel",
					 Matchers.notNullValue(),
					"products.reorderLevel",
					 is(products.getReorderLevel()),
					"products.discontinued",
					 Matchers.notNullValue(),
					"products.discontinued",
					 is(products.getDiscontinued())
					);
  	}
  
  	@Test
  	public void givenANewProductsWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewProductsRequest products = new NewProductsRequest();
			products.setProductId(2);
			products.setProductName("productName");
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(5.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(2);
			final var categoriesCategoryIdEntity= createCategories("");
			products.setCategoryId( categoriesCategoryIdEntity.getCategoryId());
			final var suppliersSupplierIdEntity= createSuppliers("");
			products.setSupplierId( suppliersSupplierIdEntity.getSupplierId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(products))
        	.post(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidProducts_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewProductsRequest products = new NewProductsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(products))
        	.when()
        	.post(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3),
            	"errors.body",
            	hasItems(
						"productId of Products must not be blank",
						"productName of Products must not be blank",
						"discontinued of Products must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentProducts_whenExecuteCreateEndpoint_shouldReturnConflictProductsRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var productsEntity = createProducts("");
		NewProductsRequest products = new NewProductsRequest();
			products.setProductId(productsEntity.getProductId());
			products.setProductName("productName");
			final var suppliersSupplierIdEntity= createSuppliers("");
			products.setSupplierId( suppliersSupplierIdEntity.getSupplierId());
			final var categoriesCategoryIdEntity= createCategories("");
			products.setCategoryId( categoriesCategoryIdEntity.getCategoryId());
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(0.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(2);
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(products))
        	.post(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("products already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentProducts_whenExecuteUpdateEndpoint_shouldReturnUpdatedProducts()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var productsEntity = createProducts("");
		UpdateProductsRequest products = new UpdateProductsRequest();
			products.setProductId(productsEntity.getProductId());
			products.setProductName("productName");
			final var suppliersSupplierIdEntity= createSuppliers("");
			products.setSupplierId( suppliersSupplierIdEntity.getSupplierId());
			final var categoriesCategoryIdEntity= createCategories("");
			products.setCategoryId( categoriesCategoryIdEntity.getCategoryId());
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(3.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(7);
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(products))
        	.put(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"products.productId",
				 Matchers.notNullValue(),
				"products.productId",
				 is(products.getProductId()),
				"products.productName",
				 Matchers.notNullValue(),
				"products.productName",
				 is(products.getProductName()),
				"products.suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products.suppliersSupplierIdResponse.supplierId",
				 is(suppliersSupplierIdEntity.getSupplierId()),
				"products.categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products.categoriesCategoryIdResponse.categoryId",
				 is(categoriesCategoryIdEntity.getCategoryId()),
				"products.quantityPerUnit",
				 Matchers.notNullValue(),
				"products.quantityPerUnit",
				 is(products.getQuantityPerUnit()),
				"products.unitPrice",
				 Matchers.notNullValue(),
				"products.unitPrice",
				 is(products.getUnitPrice()),
				"products.unitsInStock",
				 Matchers.notNullValue(),
				"products.unitsInStock",
				 is(products.getUnitsInStock()),
				"products.unitsOnOrder",
				 Matchers.notNullValue(),
				"products.unitsOnOrder",
				 is(products.getUnitsOnOrder()),
				"products.reorderLevel",
				 Matchers.notNullValue(),
				"products.reorderLevel",
				 is(products.getReorderLevel()),
				"products.discontinued",
				 Matchers.notNullValue(),
				"products.discontinued",
				 is(products.getDiscontinued())
        		);
        	
			Assertions.assertEquals(products.getProductId(),
			 productsEntity.getProductId());Assertions.assertEquals(products.getProductName(),
			 productsEntity.getProductName());Assertions.assertEquals(products.getSupplierId(),
			 suppliersSupplierIdEntity.getSupplierId());Assertions.assertEquals(products.getCategoryId(),
			 categoriesCategoryIdEntity.getCategoryId());Assertions.assertEquals(products.getQuantityPerUnit(),
			 productsEntity.getQuantityPerUnit());Assertions.assertEquals(products.getUnitPrice(),
			 productsEntity.getUnitPrice());Assertions.assertEquals(products.getUnitsInStock(),
			 productsEntity.getUnitsInStock());Assertions.assertEquals(products.getUnitsOnOrder(),
			 productsEntity.getUnitsOnOrder());Assertions.assertEquals(products.getReorderLevel(),
			 productsEntity.getReorderLevel());Assertions.assertEquals(products.getDiscontinued(),
			 productsEntity.getDiscontinued());
  	}
  
  	@Test
  	public void givenAExistentProductsWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var productsEntity = createProducts("");
		UpdateProductsRequest products = new UpdateProductsRequest();
			products.setProductId(productsEntity.getProductId());
			products.setProductName("productName");
			final var suppliersSupplierIdEntity= createSuppliers("");
			products.setSupplierId( suppliersSupplierIdEntity.getSupplierId());
			final var categoriesCategoryIdEntity= createCategories("");
			products.setCategoryId( categoriesCategoryIdEntity.getCategoryId());
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(6.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(2);
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(products))
        	.put(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentProducts_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateProductsRequest products = new UpdateProductsRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(products))
        	.when()
        	.put(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"productId of Products must not be blank",
						"productName of Products must not be blank",
						"discontinued of Products must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentProducts_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateProductsRequest products = new UpdateProductsRequest();
			products.setProductId(2);
			products.setProductName("productName");
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(6.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(7);
			final var categoriesCategoryIdEntity= createCategories("");
			products.setCategoryId( categoriesCategoryIdEntity.getCategoryId());
			final var suppliersSupplierIdEntity= createSuppliers("");
			products.setSupplierId( suppliersSupplierIdEntity.getSupplierId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(products))
        	.when()
        	.put(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("products not found"));
  	}
  
  	@Test
  	public void givenANewProductsWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewProductsRequest products = new NewProductsRequest();
			products.setProductId(2);
			products.setProductName("productName");
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(1.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(7);
			products.setCategoryId(2);
			products.setSupplierId(2);
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(products))
        	.when()
        	.post(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("categories not found"),hasItems("suppliers not found")));
  	}
  
   	@Test
   	public void givenAExistentProductsWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var productsEntity = createProducts("");
		UpdateProductsRequest products = new UpdateProductsRequest();
			products.setProductId(productsEntity.getProductId());
			products.setProductName("productName");
			products.setSupplierId(2);
			products.setCategoryId(2);
			products.setQuantityPerUnit("quantityPerUnit");
			products.setUnitPrice(4.0);
			products.setUnitsInStock(2);
			products.setUnitsOnOrder(2);
			products.setReorderLevel(2);
			products.setDiscontinued(2);
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(products))
         	.when()
         	.put(PRODUCTS_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("products not found"),hasItems("categories not found"),hasItems("suppliers not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentProducts_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("productId", 2)
 	        .delete(PRODUCTS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentProducts_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var productsEntity = createProducts("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("productId", productsEntity.getProductId())
 	        .delete(PRODUCTS_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findProductsEntityByPK(
	 productsEntity.getProductId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentProductsWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var productsEntity = createProducts("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("productId", productsEntity.getProductId())
        	.delete(PRODUCTS_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentProducts_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var productsEntity = createProducts("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("productId", productsEntity.getProductId())
			.get(PRODUCTS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"products.productId",
				 Matchers.notNullValue(),
				"products.productId",
				 is(productsEntity.getProductId()),
				"products.productName",
				 Matchers.notNullValue(),
				"products.productName",
				 is(productsEntity.getProductName()),
				"products.suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products.suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products.categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products.categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products.quantityPerUnit",
				 Matchers.notNullValue(),
				"products.quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products.unitPrice",
				 Matchers.notNullValue(),
				"products.unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products.unitsInStock",
				 Matchers.notNullValue(),
				"products.unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products.unitsOnOrder",
				 Matchers.notNullValue(),
				"products.unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products.reorderLevel",
				 Matchers.notNullValue(),
				"products.reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products.discontinued",
				 Matchers.notNullValue(),
				"products.discontinued",
				 is(productsEntity.getDiscontinued())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentProducts_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var productsEntity = createProducts("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("productId", 2)
			.get(PRODUCTS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentProductsWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var productsEntity = createProducts("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("productId", productsEntity.getProductId())
			.get(PRODUCTS_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"products.productId",
				 Matchers.notNullValue(),
				"products.productId",
				 is(productsEntity.getProductId()),
				"products.productName",
				 Matchers.notNullValue(),
				"products.productName",
				 is(productsEntity.getProductName()),
				"products.suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products.suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products.categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products.categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products.quantityPerUnit",
				 Matchers.notNullValue(),
				"products.quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products.unitPrice",
				 Matchers.notNullValue(),
				"products.unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products.unitsInStock",
				 Matchers.notNullValue(),
				"products.unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products.unitsOnOrder",
				 Matchers.notNullValue(),
				"products.unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products.reorderLevel",
				 Matchers.notNullValue(),
				"products.reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products.discontinued",
				 Matchers.notNullValue(),
				"products.discontinued",
				 is(productsEntity.getDiscontinued())
				);
	}
   
	@Test
	public void given10Products_whenExecuteFindProductsByFilterWithOffset0AndLimit5_shouldReturnListOf5Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"products[0]",
					 hasKey("productId"),
					"products[0]",
					 hasKey("productName"),
					"products[0]",
					 hasKey("suppliersSupplierIdResponse"),
					"products[0]",
					 hasKey("categoriesCategoryIdResponse"),
					"products[0]",
					 hasKey("quantityPerUnit"),
					"products[0]",
					 hasKey("unitPrice"),
					"products[0]",
					 hasKey("unitsInStock"),
					"products[0]",
					 hasKey("unitsOnOrder"),
					"products[0]",
					 hasKey("reorderLevel"),
					"products[0]",
					 hasKey("discontinued") ,
					"productsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilterWithOffset0AndLimit10_shouldReturnListOf10Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"products[0]",
					 hasKey("productId"),
					"products[0]",
					 hasKey("productName"),
					"products[0]",
					 hasKey("suppliersSupplierIdResponse"),
					"products[0]",
					 hasKey("categoriesCategoryIdResponse"),
					"products[0]",
					 hasKey("quantityPerUnit"),
					"products[0]",
					 hasKey("unitPrice"),
					"products[0]",
					 hasKey("unitsInStock"),
					"products[0]",
					 hasKey("unitsOnOrder"),
					"products[0]",
					 hasKey("reorderLevel"),
					"products[0]",
					 hasKey("discontinued") ,
					"productsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilterWithOffset0AndLimit15_shouldReturnListOf10Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"products[0]",
					 hasKey("productId"),
					"products[0]",
					 hasKey("productName"),
					"products[0]",
					 hasKey("suppliersSupplierIdResponse"),
					"products[0]",
					 hasKey("categoriesCategoryIdResponse"),
					"products[0]",
					 hasKey("quantityPerUnit"),
					"products[0]",
					 hasKey("unitPrice"),
					"products[0]",
					 hasKey("unitsInStock"),
					"products[0]",
					 hasKey("unitsOnOrder"),
					"products[0]",
					 hasKey("reorderLevel"),
					"products[0]",
					 hasKey("discontinued") ,
					"productsCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Products_whenExecuteFindProductsByFilterWithOffset10AndLimit5_shouldReturnListOf5Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"products[0]",
					 hasKey("productId"),
					"products[0]",
					 hasKey("productName"),
					"products[0]",
					 hasKey("suppliersSupplierIdResponse"),
					"products[0]",
					 hasKey("categoriesCategoryIdResponse"),
					"products[0]",
					 hasKey("quantityPerUnit"),
					"products[0]",
					 hasKey("unitPrice"),
					"products[0]",
					 hasKey("unitsInStock"),
					"products[0]",
					 hasKey("unitsOnOrder"),
					"products[0]",
					 hasKey("reorderLevel"),
					"products[0]",
					 hasKey("discontinued") ,
					"productsCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilterWithOffset20AndLimit5_shouldReturnListOf0Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"productsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilterWithOffset10AndLimit10_shouldReturnListOf0Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"productsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilterWithOffset10AndLimit115_shouldReturnListOf0Products() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createProducts(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"productsCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							""+"||"+"productName|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 is(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 is(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 is(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							""+"||"+"productName|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 is(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 is(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 is(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|neq|"+productsEntity.getProductId()+
							""+"||"+"productName|neq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|neq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|neq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|neq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|neq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|neq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|neq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|neq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|neq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 not(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 not(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 not(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 not(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 not(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 not(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 not(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 not(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 not(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 not(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|like|"+productsEntity.getProductId()+
							""+"||"+"productName|like|"+productsEntity.getProductName()+
							""+"||"+"supplierId|like|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|like|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|like|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|like|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|like|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|like|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|like|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|like|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND,AND,AND,AND,AND"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 is(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 is(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 is(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|nlike|"+productsEntity.getProductId()+
							""+"||"+"productName|nlike|"+productsEntity.getProductName()+
							""+"||"+"supplierId|nlike|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|nlike|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|nlike|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|nlike|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|nlike|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|nlike|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|nlike|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|nlike|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 not(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 not(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 not(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 not(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 not(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 not(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 not(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 not(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 not(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 not(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							""+"||"+"productName|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","productId,productName,supplierId,categoryId,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 is(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 is(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 is(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredProducts() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							""+"||"+"productName|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-productId,-productName,-supplierId,-categoryId,-quantityPerUnit,-unitPrice,-unitsInStock,-unitsOnOrder,-reorderLevel,-discontinued"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"products[0].productId",
				 Matchers.notNullValue(),
				"products[0].productId",
				 is(productsEntity.getProductId()),
				"products[0].productName",
				 Matchers.notNullValue(),
				"products[0].productName",
				 is(productsEntity.getProductName()),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 Matchers.notNullValue(),
				"products[0].suppliersSupplierIdResponse.supplierId",
				 is(productsEntity.getSuppliersSupplierId().getSupplierId()),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 Matchers.notNullValue(),
				"products[0].categoriesCategoryIdResponse.categoryId",
				 is(productsEntity.getCategoriesCategoryId().getCategoryId()),
				"products[0].quantityPerUnit",
				 Matchers.notNullValue(),
				"products[0].quantityPerUnit",
				 is(productsEntity.getQuantityPerUnit()),
				"products[0].unitPrice",
				 Matchers.notNullValue(),
				"products[0].unitPrice",
				 is(productsEntity.getUnitPrice()),
				"products[0].unitsInStock",
				 Matchers.notNullValue(),
				"products[0].unitsInStock",
				 is(productsEntity.getUnitsInStock()),
				"products[0].unitsOnOrder",
				 Matchers.notNullValue(),
				"products[0].unitsOnOrder",
				 is(productsEntity.getUnitsOnOrder()),
				"products[0].reorderLevel",
				 Matchers.notNullValue(),
				"products[0].reorderLevel",
				 is(productsEntity.getReorderLevel()),
				"products[0].discontinued",
				 Matchers.notNullValue(),
				"products[0].discontinued",
				 is(productsEntity.getDiscontinued())
				);
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productIdXXX|eq|"+productsEntity.getProductId()+
							""+"||"+"productNameXXX|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierIdXXX|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryIdXXX|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnitXXX|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPriceXXX|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStockXXX|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrderXXX|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevelXXX|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinuedXXX|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","productId,productName,supplierId,categoryId,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eqXXX|"+productsEntity.getProductId()+
							""+"||"+"productName|eqXXX|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eqXXX|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eqXXX|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eqXXX|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eqXXX|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eqXXX|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eqXXX|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eqXXX|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eqXXX|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","productId,productName,supplierId,categoryId,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							""+"||"+"productName|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","productId,productName,supplierId,categoryId,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							""+"||"+"productName|eq|"+productsEntity.getProductName()+
							""+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							""+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							""+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							""+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							""+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							""+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							""+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							""+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","productIdXXX,productNameXXX,supplierIdXXX,categoryIdXXX,quantityPerUnitXXX,unitPriceXXX,unitsInStockXXX,unitsOnOrderXXX,reorderLevelXXX,discontinuedXXX"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Products_whenExecuteFindProductsByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var productsEntity = createProducts("");
	    for (int i = 0; i < 9; i++) {
	    	createProducts(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "productId|eq|"+productsEntity.getProductId()+
							";:')"+"||"+"productName|eq|"+productsEntity.getProductName()+
							";:')"+"||"+"supplierId|eq|"+productsEntity.getSuppliersSupplierId().getSupplierId()+
							";:')"+"||"+"categoryId|eq|"+productsEntity.getCategoriesCategoryId().getCategoryId()+
							";:')"+"||"+"quantityPerUnit|eq|"+productsEntity.getQuantityPerUnit()+
							";:')"+"||"+"unitPrice|eq|"+productsEntity.getUnitPrice()+
							";:')"+"||"+"unitsInStock|eq|"+productsEntity.getUnitsInStock()+
							";:')"+"||"+"unitsOnOrder|eq|"+productsEntity.getUnitsOnOrder()+
							";:')"+"||"+"reorderLevel|eq|"+productsEntity.getReorderLevel()+
							";:')"+"||"+"discontinued|eq|"+productsEntity.getDiscontinued()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","productId,productName,supplierId,categoryId,quantityPerUnit,unitPrice,unitsInStock,unitsOnOrder,reorderLevel,discontinued"
	        )
	        .get(PRODUCTS_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
