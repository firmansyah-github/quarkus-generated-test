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
import firmansyah.application.web.model.request.NewCategoriesRequest;
import firmansyah.application.web.model.request.UpdateCategoriesRequest;
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
public class CategoriesResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String CATEGORIES_RESOURCE_PATH = API_PREFIX + "/firmansyah/categories";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewCategories_whenExecuteCreateEndpoint_shouldReturnCreatedCategories201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewCategoriesRequest categories = new NewCategoriesRequest();
			categories.setCategoryId(2);
			categories.setCategoryName("categoryName");
			categories.setDescription("description");
			categories.setPicture(new String("picture").getBytes());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(categories)).when()
		       .post(CATEGORIES_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"categories.categoryId",
					 Matchers.notNullValue(),
					"categories.categoryId",
					 is(categories.getCategoryId()),
					"categories.categoryName",
					 Matchers.notNullValue(),
					"categories.categoryName",
					 is(categories.getCategoryName()),
					"categories.description",
					 Matchers.notNullValue(),
					"categories.description",
					 is(categories.getDescription()),
					"categories.picture",
					 Matchers.notNullValue(),
					"categories.picture",
					 is(Base64.getEncoder().encodeToString(categories.getPicture()))
					);
  	}
  
  	@Test
  	public void givenANewCategoriesWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewCategoriesRequest categories = new NewCategoriesRequest();
			categories.setCategoryId(2);
			categories.setCategoryName("categoryName");
			categories.setDescription("description");
			categories.setPicture(new String("picture").getBytes());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(categories))
        	.post(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidCategories_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewCategoriesRequest categories = new NewCategoriesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(categories))
        	.when()
        	.post(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2),
            	"errors.body",
            	hasItems(
						"categoryId of Categories must not be blank",
						"categoryName of Categories must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentCategories_whenExecuteCreateEndpoint_shouldReturnConflictCategoriesRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var categoriesEntity = createCategories("");
		NewCategoriesRequest categories = new NewCategoriesRequest();
			categories.setCategoryId(categoriesEntity.getCategoryId());
			categories.setCategoryName("categoryName");
			categories.setDescription("description");
			categories.setPicture(new String("picture").getBytes());
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(categories))
        	.post(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("categories already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentCategories_whenExecuteUpdateEndpoint_shouldReturnUpdatedCategories()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var categoriesEntity = createCategories("");
		UpdateCategoriesRequest categories = new UpdateCategoriesRequest();
			categories.setCategoryId(categoriesEntity.getCategoryId());
			categories.setCategoryName("categoryName");
			categories.setDescription("description");
			categories.setPicture(new String("picture").getBytes());
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(categories))
        	.put(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"categories.categoryId",
				 Matchers.notNullValue(),
				"categories.categoryId",
				 is(categories.getCategoryId()),
				"categories.categoryName",
				 Matchers.notNullValue(),
				"categories.categoryName",
				 is(categories.getCategoryName()),
				"categories.description",
				 Matchers.notNullValue(),
				"categories.description",
				 is(categories.getDescription()),
				"categories.picture",
				 Matchers.notNullValue(),
				"categories.picture",
				 is(Base64.getEncoder().encodeToString(categories.getPicture()))
        		);
        	
			Assertions.assertEquals(categories.getCategoryId(),
			 categoriesEntity.getCategoryId());Assertions.assertEquals(categories.getCategoryName(),
			 categoriesEntity.getCategoryName());Assertions.assertEquals(categories.getDescription(),
			 categoriesEntity.getDescription());
  	}
  
  	@Test
  	public void givenAExistentCategoriesWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var categoriesEntity = createCategories("");
		UpdateCategoriesRequest categories = new UpdateCategoriesRequest();
			categories.setCategoryId(categoriesEntity.getCategoryId());
			categories.setCategoryName("categoryName");
			categories.setDescription("description");
			categories.setPicture(new String("picture").getBytes());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(categories))
        	.put(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentCategories_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateCategoriesRequest categories = new UpdateCategoriesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(categories))
        	.when()
        	.put(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(2+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"categoryId of Categories must not be blank",
						"categoryName of Categories must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentCategories_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateCategoriesRequest categories = new UpdateCategoriesRequest();
			categories.setCategoryId(2);
			categories.setCategoryName("categoryName");
			categories.setDescription("description");
			categories.setPicture(new String("picture").getBytes());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(categories))
        	.when()
        	.put(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("categories not found"));
  	}
  
     
   	
	@Test
   	public void givenANonExistentCategories_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("categoryId", 2)
 	        .delete(CATEGORIES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentCategories_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var categoriesEntity = createCategories("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("categoryId", categoriesEntity.getCategoryId())
 	        .delete(CATEGORIES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findCategoriesEntityByPK(
	 categoriesEntity.getCategoryId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentCategoriesWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var categoriesEntity = createCategories("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("categoryId", categoriesEntity.getCategoryId())
        	.delete(CATEGORIES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentCategories_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var categoriesEntity = createCategories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("categoryId", categoriesEntity.getCategoryId())
			.get(CATEGORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"categories.categoryId",
				 Matchers.notNullValue(),
				"categories.categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories.categoryName",
				 Matchers.notNullValue(),
				"categories.categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories.description",
				 Matchers.notNullValue(),
				"categories.description",
				 is(categoriesEntity.getDescription()),
				"categories.picture",
				 Matchers.notNullValue(),
				"categories.picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentCategories_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var categoriesEntity = createCategories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("categoryId", 2)
			.get(CATEGORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentCategoriesWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var categoriesEntity = createCategories("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("categoryId", categoriesEntity.getCategoryId())
			.get(CATEGORIES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"categories.categoryId",
				 Matchers.notNullValue(),
				"categories.categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories.categoryName",
				 Matchers.notNullValue(),
				"categories.categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories.description",
				 Matchers.notNullValue(),
				"categories.description",
				 is(categoriesEntity.getDescription()),
				"categories.picture",
				 Matchers.notNullValue(),
				"categories.picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
	}
   
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilterWithOffset0AndLimit5_shouldReturnListOf5Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"categories[0]",
					 hasKey("categoryId"),
					"categories[0]",
					 hasKey("categoryName"),
					"categories[0]",
					 hasKey("description"),
					"categories[0]",
					 hasKey("picture") ,
					"categoriesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilterWithOffset0AndLimit10_shouldReturnListOf10Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"categories[0]",
					 hasKey("categoryId"),
					"categories[0]",
					 hasKey("categoryName"),
					"categories[0]",
					 hasKey("description"),
					"categories[0]",
					 hasKey("picture") ,
					"categoriesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilterWithOffset0AndLimit15_shouldReturnListOf10Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"categories[0]",
					 hasKey("categoryId"),
					"categories[0]",
					 hasKey("categoryName"),
					"categories[0]",
					 hasKey("description"),
					"categories[0]",
					 hasKey("picture") ,
					"categoriesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Categories_whenExecuteFindCategoriesByFilterWithOffset10AndLimit5_shouldReturnListOf5Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"categories[0]",
					 hasKey("categoryId"),
					"categories[0]",
					 hasKey("categoryName"),
					"categories[0]",
					 hasKey("description"),
					"categories[0]",
					 hasKey("picture") ,
					"categoriesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilterWithOffset20AndLimit5_shouldReturnListOf0Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"categoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilterWithOffset10AndLimit10_shouldReturnListOf0Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"categoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilterWithOffset10AndLimit115_shouldReturnListOf0Categories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createCategories(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"categoriesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 is(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 is(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|neq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|neq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|neq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|neq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 not(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 not(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 not(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 not(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|like|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|like|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|like|"+categoriesEntity.getDescription()+
							""+"||"+"picture|like|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 is(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|nlike|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|nlike|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|nlike|"+categoriesEntity.getDescription()+
							""+"||"+"picture|nlike|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 not(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 not(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 not(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 not(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","categoryId,categoryName,description,picture"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 is(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredCategories() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","-categoryId,-categoryName,-description,-picture"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"categories[0].categoryId",
				 Matchers.notNullValue(),
				"categories[0].categoryId",
				 is(categoriesEntity.getCategoryId()),
				"categories[0].categoryName",
				 Matchers.notNullValue(),
				"categories[0].categoryName",
				 is(categoriesEntity.getCategoryName()),
				"categories[0].description",
				 Matchers.notNullValue(),
				"categories[0].description",
				 is(categoriesEntity.getDescription()),
				"categories[0].picture",
				 Matchers.notNullValue(),
				"categories[0].picture",
				 is(Base64.getEncoder().encodeToString(categoriesEntity.getPicture()))
				);
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryIdXXX|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryNameXXX|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"descriptionXXX|eq|"+categoriesEntity.getDescription()+
							""+"||"+"pictureXXX|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","categoryId,categoryName,description,picture"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eqXXX|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eqXXX|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eqXXX|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eqXXX|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","categoryId,categoryName,description,picture"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","categoryId,categoryName,description,picture"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							""+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							""+"||"+"description|eq|"+categoriesEntity.getDescription()+
							""+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","categoryIdXXX,categoryNameXXX,descriptionXXX,pictureXXX"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Categories_whenExecuteFindCategoriesByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var categoriesEntity = createCategories("");
	    for (int i = 0; i < 9; i++) {
	    	createCategories(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "categoryId|eq|"+categoriesEntity.getCategoryId()+
							";:')"+"||"+"categoryName|eq|"+categoriesEntity.getCategoryName()+
							";:')"+"||"+"description|eq|"+categoriesEntity.getDescription()+
							";:')"+"||"+"picture|eq|"+Base64.getEncoder().encodeToString(categoriesEntity.getPicture())+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR"
	        )
	        .queryParam("sort","categoryId,categoryName,description,picture"
	        )
	        .get(CATEGORIES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
