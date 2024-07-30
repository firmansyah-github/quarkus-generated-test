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
import firmansyah.application.web.model.request.NewEmployeesRequest;
import firmansyah.application.web.model.request.UpdateEmployeesRequest;
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
public class EmployeesResourceIntegrationTest extends ResourcesIntegrationTest {

	private final String EMPLOYEES_RESOURCE_PATH = API_PREFIX + "/firmansyah/employees";
  
    @BeforeEach
    public void setup() {
        RestAssured.config = RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                .setParam("http.socket.timeout", 600000)  // 60 seconds
                .setParam("http.connection.timeout", 600000));  // 60 seconds
    } 
    
    
	@Test
  	public void givenANewEmployees_whenExecuteCreateEndpoint_shouldReturnCreatedEmployees201() throws JsonProcessingException {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		NewEmployeesRequest employees = new NewEmployeesRequest();
			employees.setEmployeeId(2);
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			employees.setPhotoPath("photoPath");
			final var employeesReportsToEntity= createEmployees("");
			employees.setReportsTo( employeesReportsToEntity.getEmployeeId());
			

		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
			   .body(objectMapper.writeValueAsString(employees)).when()
		       .post(EMPLOYEES_RESOURCE_PATH).then().statusCode(HttpStatus.SC_CREATED)
		       .body(
					"employees.employeeId",
					 Matchers.notNullValue(),
					"employees.employeeId",
					 is(employees.getEmployeeId()),
					"employees.lastName",
					 Matchers.notNullValue(),
					"employees.lastName",
					 is(employees.getLastName()),
					"employees.firstName",
					 Matchers.notNullValue(),
					"employees.firstName",
					 is(employees.getFirstName()),
					"employees.title",
					 Matchers.notNullValue(),
					"employees.title",
					 is(employees.getTitle()),
					"employees.titleOfCourtesy",
					 Matchers.notNullValue(),
					"employees.titleOfCourtesy",
					 is(employees.getTitleOfCourtesy()),
					"employees.birthDate",
					 Matchers.notNullValue(),
					"employees.birthDate",
					 is(employees.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"employees.hireDate",
					 Matchers.notNullValue(),
					"employees.hireDate",
					 is(employees.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
					"employees.address",
					 Matchers.notNullValue(),
					"employees.address",
					 is(employees.getAddress()),
					"employees.city",
					 Matchers.notNullValue(),
					"employees.city",
					 is(employees.getCity()),
					"employees.region",
					 Matchers.notNullValue(),
					"employees.region",
					 is(employees.getRegion()),
					"employees.postalCode",
					 Matchers.notNullValue(),
					"employees.postalCode",
					 is(employees.getPostalCode()),
					"employees.country",
					 Matchers.notNullValue(),
					"employees.country",
					 is(employees.getCountry()),
					"employees.homePhone",
					 Matchers.notNullValue(),
					"employees.homePhone",
					 is(employees.getHomePhone()),
					"employees.extension",
					 Matchers.notNullValue(),
					"employees.extension",
					 is(employees.getExtension()),
					"employees.photo",
					 Matchers.notNullValue(),
					"employees.photo",
					 is(Base64.getEncoder().encodeToString(employees.getPhoto())),
					"employees.notes",
					 Matchers.notNullValue(),
					"employees.notes",
					 is(employees.getNotes()),
					"employees.employeesReportsToResponse.employeeId",
					 Matchers.notNullValue(),
					"employees.employeesReportsToResponse.employeeId",
					 is( employeesReportsToEntity.getEmployeeId()),
					"employees.photoPath",
					 Matchers.notNullValue(),
					"employees.photoPath",
					 is(employees.getPhotoPath())
					);
  	}
  
  	@Test
  	public void givenANewEmployeesWithoutAuthorizationHeader_whenExecuteCreateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		NewEmployeesRequest employees = new NewEmployeesRequest();
			employees.setEmployeeId(2);
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			employees.setPhotoPath("photoPath");
			final var employeesReportsToEntity= createEmployees("");
			employees.setReportsTo( employeesReportsToEntity.getEmployeeId());
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(employees))
        	.post(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
    }
  
  	@Test
  	public void givenAnInvalidEmployees_whenExecuteCreateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	NewEmployeesRequest employees = new NewEmployeesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employees))
        	.when()
        	.post(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3),
            	"errors.body",
            	hasItems(
						"employeeId of Employees must not be blank",
						"lastName of Employees must not be blank",
						"firstName of Employees must not be blank"
						));
  	}
  	
  	
  	@Test
  	public void givenAExistentEmployees_whenExecuteCreateEndpoint_shouldReturnConflictEmployeesRC409()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	  	
		final var employeesEntity = createEmployees("");
		NewEmployeesRequest employees = new NewEmployeesRequest();
			employees.setEmployeeId(employeesEntity.getEmployeeId());
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			final var employeesReportsToEntity= createEmployees("");
			employees.setReportsTo( employeesReportsToEntity.getEmployeeId());
			employees.setPhotoPath("photoPath");
			
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employees))
        	.post(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_CONFLICT)
        	.body("errors.body", hasItems("employees already exists"));
        	
  	}
  	
  	
  
  	@Test
  	public void givenAExistentEmployees_whenExecuteUpdateEndpoint_shouldReturnUpdatedEmployees()
      	throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
    
		final var employeesEntity = createEmployees("");
		UpdateEmployeesRequest employees = new UpdateEmployeesRequest();
			employees.setEmployeeId(employeesEntity.getEmployeeId());
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			final var employeesReportsToEntity= createEmployees("");
			employees.setReportsTo( employeesReportsToEntity.getEmployeeId());
			employees.setPhotoPath("photoPath");
			

    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employees))
        	.put(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_OK)
        	.body(
				"employees.employeeId",
				 Matchers.notNullValue(),
				"employees.employeeId",
				 is(employees.getEmployeeId()),
				"employees.lastName",
				 Matchers.notNullValue(),
				"employees.lastName",
				 is(employees.getLastName()),
				"employees.firstName",
				 Matchers.notNullValue(),
				"employees.firstName",
				 is(employees.getFirstName()),
				"employees.title",
				 Matchers.notNullValue(),
				"employees.title",
				 is(employees.getTitle()),
				"employees.titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees.titleOfCourtesy",
				 is(employees.getTitleOfCourtesy()),
				"employees.birthDate",
				 Matchers.notNullValue(),
				"employees.birthDate",
				 is(employees.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees.hireDate",
				 Matchers.notNullValue(),
				"employees.hireDate",
				 is(employees.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees.address",
				 Matchers.notNullValue(),
				"employees.address",
				 is(employees.getAddress()),
				"employees.city",
				 Matchers.notNullValue(),
				"employees.city",
				 is(employees.getCity()),
				"employees.region",
				 Matchers.notNullValue(),
				"employees.region",
				 is(employees.getRegion()),
				"employees.postalCode",
				 Matchers.notNullValue(),
				"employees.postalCode",
				 is(employees.getPostalCode()),
				"employees.country",
				 Matchers.notNullValue(),
				"employees.country",
				 is(employees.getCountry()),
				"employees.homePhone",
				 Matchers.notNullValue(),
				"employees.homePhone",
				 is(employees.getHomePhone()),
				"employees.extension",
				 Matchers.notNullValue(),
				"employees.extension",
				 is(employees.getExtension()),
				"employees.photo",
				 Matchers.notNullValue(),
				"employees.photo",
				 is(Base64.getEncoder().encodeToString(employees.getPhoto())),
				"employees.notes",
				 Matchers.notNullValue(),
				"employees.notes",
				 is(employees.getNotes()),
				"employees.employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees.employeesReportsToResponse.employeeId",
				 is(employeesReportsToEntity.getEmployeeId()),
				"employees.photoPath",
				 Matchers.notNullValue(),
				"employees.photoPath",
				 is(employees.getPhotoPath())
        		);
        	
			Assertions.assertEquals(employees.getEmployeeId(),
			 employeesEntity.getEmployeeId());Assertions.assertEquals(employees.getLastName(),
			 employeesEntity.getLastName());Assertions.assertEquals(employees.getFirstName(),
			 employeesEntity.getFirstName());Assertions.assertEquals(employees.getTitle(),
			 employeesEntity.getTitle());Assertions.assertEquals(employees.getTitleOfCourtesy(),
			 employeesEntity.getTitleOfCourtesy());Assertions.assertEquals(employees.getAddress(),
			 employeesEntity.getAddress());Assertions.assertEquals(employees.getCity(),
			 employeesEntity.getCity());Assertions.assertEquals(employees.getRegion(),
			 employeesEntity.getRegion());Assertions.assertEquals(employees.getPostalCode(),
			 employeesEntity.getPostalCode());Assertions.assertEquals(employees.getCountry(),
			 employeesEntity.getCountry());Assertions.assertEquals(employees.getHomePhone(),
			 employeesEntity.getHomePhone());Assertions.assertEquals(employees.getExtension(),
			 employeesEntity.getExtension());Assertions.assertEquals(employees.getNotes(),
			 employeesEntity.getNotes());Assertions.assertEquals(employees.getReportsTo(),
			 employeesReportsToEntity.getEmployeeId());Assertions.assertEquals(employees.getPhotoPath(),
			 employeesEntity.getPhotoPath());
  	}
  
  	@Test
  	public void givenAExistentEmployeesWithoutAuthorizationHeader_whenExecuteUpdateEndpoint_shouldReturnUnauthorized401()
        throws JsonProcessingException {
    
		final var employeesEntity = createEmployees("");
		UpdateEmployeesRequest employees = new UpdateEmployeesRequest();
			employees.setEmployeeId(employeesEntity.getEmployeeId());
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			final var employeesReportsToEntity= createEmployees("");
			employees.setReportsTo( employeesReportsToEntity.getEmployeeId());
			employees.setPhotoPath("photoPath");
			
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
        	.body(objectMapper.writeValueAsString(employees))
        	.put(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
  
  	@Test
  	public void givenAnInvalidExistentEmployees_whenExecuteUpdateEndpoint_thenReturnErrorsWith422Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

	  	UpdateEmployeesRequest employees = new UpdateEmployeesRequest();
	  

	  	given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employees))
        	.when()
        	.put(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        	.body(
            	"errors.body",
            	hasSize(3+1),
            	"errors.body",
            	hasItems("At least one field must be not null",							"employeeId of Employees must not be blank",
						"lastName of Employees must not be blank",
						"firstName of Employees must not be blank"
));
  	}
  
   	@Test
  	public void givenANonExistentEmployees_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		UpdateEmployeesRequest employees = new UpdateEmployeesRequest();
			employees.setEmployeeId(2);
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			employees.setPhotoPath("photoPath");
			final var employeesReportsToEntity= createEmployees("");
			employees.setReportsTo( employeesReportsToEntity.getEmployeeId());
			
	  
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employees))
        	.when()
        	.put(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body("errors.body", hasItems("employees not found"));
  	}
  
  	@Test
  	public void givenANewEmployeesWithoutExistentParents_whenExecuteCreateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		NewEmployeesRequest employees = new NewEmployeesRequest();
			employees.setEmployeeId(2);
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			employees.setPhotoPath("photoPath");
			employees.setReportsTo(2);
			
	 
		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
        	.body(objectMapper.writeValueAsString(employees))
        	.when()
        	.post(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_NOT_FOUND)
        	.body(
           		"errors.body", 
           		anyOf(hasItems("employees not found")));
  	}
  
   	@Test
   	public void givenAExistentEmployeesWithoutExistentParents_whenExecuteUpdateEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

		final var employeesEntity = createEmployees("");
		UpdateEmployeesRequest employees = new UpdateEmployeesRequest();
			employees.setEmployeeId(employeesEntity.getEmployeeId());
			employees.setLastName("lastName");
			employees.setFirstName("firstName");
			employees.setTitle("title");
			employees.setTitleOfCourtesy("titleOfCourtesy");
			employees.setBirthDate(LocalDateTime.now());
			employees.setHireDate(LocalDateTime.now());
			employees.setAddress("address");
			employees.setCity("city");
			employees.setRegion("region");
			employees.setPostalCode("postalCode");
			employees.setCountry("country");
			employees.setHomePhone("homePhone");
			employees.setExtension("extension");
			employees.setPhoto(new String("photo").getBytes());
			employees.setNotes("notes");
			employees.setReportsTo(2);
			employees.setPhotoPath("photoPath");
			
 	 
 		given().contentType(MediaType.APPLICATION_JSON).header(AUTHORIZATION_HEADER, authorizationHeader)
         	.body(objectMapper.writeValueAsString(employees))
         	.when()
         	.put(EMPLOYEES_RESOURCE_PATH)
         	.then()
         	.statusCode(HttpStatus.SC_NOT_FOUND)
         	.body(
            	"errors.body", 
            	anyOf(hasItems("employees not found"),hasItems("employees not found")	));
   	}
     
   	
	@Test
   	public void givenANonExistentEmployees_whenExecuteDeleteEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("employeeId", 2)
 	        .delete(EMPLOYEES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_NOT_FOUND);
   	}
   
  	
   	@Test
   	public void givenAExistentEmployees_whenExecuteDeleteEndpoint_thenReturnOkStatus() throws JsonProcessingException {
 	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
 	  	String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
 	  
 	  	final var employeesEntity = createEmployees("");
 	  
 	
 	    given()
 	        .contentType(MediaType.APPLICATION_JSON)
 	        .header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("employeeId", employeesEntity.getEmployeeId())
 	        .delete(EMPLOYEES_RESOURCE_PATH)
 	        .then()
 	        .statusCode(HttpStatus.SC_OK);

 	    Assertions.assertNull(findEmployeesEntityByPK(
	 employeesEntity.getEmployeeId()
		 	         )); 
   	}
   
   	
   	@Test
  	public void givenAExistentEmployeesWithoutAuthorizationHeader_whenExecuteDeleteEndpoint_shouldReturnUnauthorized401()
		throws JsonProcessingException {
    
	  	final var employeesEntity = createEmployees("");
	
    	given()
        	.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("employeeId", employeesEntity.getEmployeeId())
        	.delete(EMPLOYEES_RESOURCE_PATH)
        	.then()
        	.statusCode(HttpStatus.SC_UNAUTHORIZED)
        	.body("errors.body", hasItems("Unauthorized"));
	}
 
  	
  	@Test
  	public void givenAExistentEmployees_whenExecuteFindByPrimaryKeyEndpoint_ReturnOkStatusAndEntityPK() throws JsonProcessingException {
	   	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   

	    final var employeesEntity = createEmployees("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
		    .queryParam("employeeId", employeesEntity.getEmployeeId())
			.get(EMPLOYEES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"employees.employeeId",
				 Matchers.notNullValue(),
				"employees.employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees.lastName",
				 Matchers.notNullValue(),
				"employees.lastName",
				 is(employeesEntity.getLastName()),
				"employees.firstName",
				 Matchers.notNullValue(),
				"employees.firstName",
				 is(employeesEntity.getFirstName()),
				"employees.title",
				 Matchers.notNullValue(),
				"employees.title",
				 is(employeesEntity.getTitle()),
				"employees.titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees.titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees.birthDate",
				 Matchers.notNullValue(),
				"employees.birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees.hireDate",
				 Matchers.notNullValue(),
				"employees.hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees.address",
				 Matchers.notNullValue(),
				"employees.address",
				 is(employeesEntity.getAddress()),
				"employees.city",
				 Matchers.notNullValue(),
				"employees.city",
				 is(employeesEntity.getCity()),
				"employees.region",
				 Matchers.notNullValue(),
				"employees.region",
				 is(employeesEntity.getRegion()),
				"employees.postalCode",
				 Matchers.notNullValue(),
				"employees.postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees.country",
				 Matchers.notNullValue(),
				"employees.country",
				 is(employeesEntity.getCountry()),
				"employees.homePhone",
				 Matchers.notNullValue(),
				"employees.homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees.extension",
				 Matchers.notNullValue(),
				"employees.extension",
				 is(employeesEntity.getExtension()),
				"employees.photo",
				 Matchers.notNullValue(),
				"employees.photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees.notes",
				 Matchers.notNullValue(),
				"employees.notes",
				 is(employeesEntity.getNotes()),
				"employees.employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees.employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees.photoPath",
				 Matchers.notNullValue(),
				"employees.photoPath",
				 is(employeesEntity.getPhotoPath())
				);
  	}
   
   	
   	@Test
  	public void givenANonExistentEmployees_whenExecuteFindByPrimaryKeyEndpoint_thenReturnErrorsWith404Code() throws JsonProcessingException {
	  	final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	   
		final var employeesEntity = createEmployees("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
			.header(AUTHORIZATION_HEADER, authorizationHeader)
			.queryParam("employeeId", 2)
			.get(EMPLOYEES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
  }
   
   
	@Test
  	public void givenAExistentEmployeesWithoutAuthorizationHeader_whenExecuteFindByPrimaryKeyEndpoint_shouldReturnAuthorized200()
		throws JsonProcessingException {
    
		final var employeesEntity = createEmployees("");

		given()
			.contentType(MediaType.APPLICATION_JSON)
		    .queryParam("employeeId", employeesEntity.getEmployeeId())
			.get(EMPLOYEES_RESOURCE_PATH+"/find")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.body(
				"employees.employeeId",
				 Matchers.notNullValue(),
				"employees.employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees.lastName",
				 Matchers.notNullValue(),
				"employees.lastName",
				 is(employeesEntity.getLastName()),
				"employees.firstName",
				 Matchers.notNullValue(),
				"employees.firstName",
				 is(employeesEntity.getFirstName()),
				"employees.title",
				 Matchers.notNullValue(),
				"employees.title",
				 is(employeesEntity.getTitle()),
				"employees.titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees.titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees.birthDate",
				 Matchers.notNullValue(),
				"employees.birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees.hireDate",
				 Matchers.notNullValue(),
				"employees.hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees.address",
				 Matchers.notNullValue(),
				"employees.address",
				 is(employeesEntity.getAddress()),
				"employees.city",
				 Matchers.notNullValue(),
				"employees.city",
				 is(employeesEntity.getCity()),
				"employees.region",
				 Matchers.notNullValue(),
				"employees.region",
				 is(employeesEntity.getRegion()),
				"employees.postalCode",
				 Matchers.notNullValue(),
				"employees.postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees.country",
				 Matchers.notNullValue(),
				"employees.country",
				 is(employeesEntity.getCountry()),
				"employees.homePhone",
				 Matchers.notNullValue(),
				"employees.homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees.extension",
				 Matchers.notNullValue(),
				"employees.extension",
				 is(employeesEntity.getExtension()),
				"employees.photo",
				 Matchers.notNullValue(),
				"employees.photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees.notes",
				 Matchers.notNullValue(),
				"employees.notes",
				 is(employeesEntity.getNotes()),
				"employees.employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees.employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees.photoPath",
				 Matchers.notNullValue(),
				"employees.photoPath",
				 is(employeesEntity.getPhotoPath())
				);
	}
   
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilterWithOffset0AndLimit5_shouldReturnListOf5Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 5)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employees[0]",
					 hasKey("employeeId"),
					"employees[0]",
					 hasKey("lastName"),
					"employees[0]",
					 hasKey("firstName"),
					"employees[0]",
					 hasKey("title"),
					"employees[0]",
					 hasKey("titleOfCourtesy"),
					"employees[0]",
					 hasKey("birthDate"),
					"employees[0]",
					 hasKey("hireDate"),
					"employees[0]",
					 hasKey("address"),
					"employees[0]",
					 hasKey("city"),
					"employees[0]",
					 hasKey("region"),
					"employees[0]",
					 hasKey("postalCode"),
					"employees[0]",
					 hasKey("country"),
					"employees[0]",
					 hasKey("homePhone"),
					"employees[0]",
					 hasKey("extension"),
					"employees[0]",
					 hasKey("photo"),
					"employees[0]",
					 hasKey("notes"),
					"employees[0]",
					 hasKey("employeesReportsToResponse"),
					"employees[0]",
					 hasKey("photoPath") ,
					"employeesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilterWithOffset0AndLimit10_shouldReturnListOf10Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employees[0]",
					 hasKey("employeeId"),
					"employees[0]",
					 hasKey("lastName"),
					"employees[0]",
					 hasKey("firstName"),
					"employees[0]",
					 hasKey("title"),
					"employees[0]",
					 hasKey("titleOfCourtesy"),
					"employees[0]",
					 hasKey("birthDate"),
					"employees[0]",
					 hasKey("hireDate"),
					"employees[0]",
					 hasKey("address"),
					"employees[0]",
					 hasKey("city"),
					"employees[0]",
					 hasKey("region"),
					"employees[0]",
					 hasKey("postalCode"),
					"employees[0]",
					 hasKey("country"),
					"employees[0]",
					 hasKey("homePhone"),
					"employees[0]",
					 hasKey("extension"),
					"employees[0]",
					 hasKey("photo"),
					"employees[0]",
					 hasKey("notes"),
					"employees[0]",
					 hasKey("employeesReportsToResponse"),
					"employees[0]",
					 hasKey("photoPath") ,
					"employeesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilterWithOffset0AndLimit15_shouldReturnListOf10Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 15)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employees[0]",
					 hasKey("employeeId"),
					"employees[0]",
					 hasKey("lastName"),
					"employees[0]",
					 hasKey("firstName"),
					"employees[0]",
					 hasKey("title"),
					"employees[0]",
					 hasKey("titleOfCourtesy"),
					"employees[0]",
					 hasKey("birthDate"),
					"employees[0]",
					 hasKey("hireDate"),
					"employees[0]",
					 hasKey("address"),
					"employees[0]",
					 hasKey("city"),
					"employees[0]",
					 hasKey("region"),
					"employees[0]",
					 hasKey("postalCode"),
					"employees[0]",
					 hasKey("country"),
					"employees[0]",
					 hasKey("homePhone"),
					"employees[0]",
					 hasKey("extension"),
					"employees[0]",
					 hasKey("photo"),
					"employees[0]",
					 hasKey("notes"),
					"employees[0]",
					 hasKey("employeesReportsToResponse"),
					"employees[0]",
					 hasKey("photoPath") ,
					"employeesCount",
					 is(10)
				);
				
	}
	
	@Test
	public void given15Employees_whenExecuteFindEmployeesByFilterWithOffset10AndLimit5_shouldReturnListOf5Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 15; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 5)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					"employees[0]",
					 hasKey("employeeId"),
					"employees[0]",
					 hasKey("lastName"),
					"employees[0]",
					 hasKey("firstName"),
					"employees[0]",
					 hasKey("title"),
					"employees[0]",
					 hasKey("titleOfCourtesy"),
					"employees[0]",
					 hasKey("birthDate"),
					"employees[0]",
					 hasKey("hireDate"),
					"employees[0]",
					 hasKey("address"),
					"employees[0]",
					 hasKey("city"),
					"employees[0]",
					 hasKey("region"),
					"employees[0]",
					 hasKey("postalCode"),
					"employees[0]",
					 hasKey("country"),
					"employees[0]",
					 hasKey("homePhone"),
					"employees[0]",
					 hasKey("extension"),
					"employees[0]",
					 hasKey("photo"),
					"employees[0]",
					 hasKey("notes"),
					"employees[0]",
					 hasKey("employeesReportsToResponse"),
					"employees[0]",
					 hasKey("photoPath") ,
					"employeesCount",
					 is(5)
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilterWithOffset20AndLimit5_shouldReturnListOf0Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 20)
	        .queryParam("limit", 5)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"employeesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilterWithOffset10AndLimit10_shouldReturnListOf0Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 10)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"employeesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilterWithOffset10AndLimit115_shouldReturnListOf0Employees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	     
	    for (int i = 0; i < 10; i++) {
	    	createEmployees(String.valueOf(i));
		}
	   

	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 10)
	        .queryParam("limit", 15)
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
					
					"employeesCount",
					 is(0)
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_And_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							""+"||"+"title|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eq|"+employeesEntity.getAddress()+
							""+"||"+"city|eq|"+employeesEntity.getCity()+
							""+"||"+"region|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eq|"+employeesEntity.getExtension()+
							""+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 is(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 is(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 is(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 is(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 is(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 is(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 is(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 is(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 is(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 is(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							""+"||"+"title|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eq|"+employeesEntity.getAddress()+
							""+"||"+"city|eq|"+employeesEntity.getCity()+
							""+"||"+"region|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eq|"+employeesEntity.getExtension()+
							""+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 is(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 is(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 is(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 is(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 is(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 is(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 is(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 is(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 is(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 is(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_NotEquals_Or_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|neq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|neq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|neq|"+employeesEntity.getFirstName()+
							""+"||"+"title|neq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|neq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|neq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|neq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|neq|"+employeesEntity.getAddress()+
							""+"||"+"city|neq|"+employeesEntity.getCity()+
							""+"||"+"region|neq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|neq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|neq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|neq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|neq|"+employeesEntity.getExtension()+
							""+"||"+"photo|neq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|neq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|neq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|neq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 not(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 not(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 not(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 not(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 not(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 not(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 not(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 not(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 not(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 not(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 not(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 not(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 not(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 not(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 not(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 not(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 not(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 not(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_Like_And_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|like|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|like|"+employeesEntity.getLastName()+
							""+"||"+"firstName|like|"+employeesEntity.getFirstName()+
							""+"||"+"title|like|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|like|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|like|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|like|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|like|"+employeesEntity.getAddress()+
							""+"||"+"city|like|"+employeesEntity.getCity()+
							""+"||"+"region|like|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|like|"+employeesEntity.getPostalCode()+
							""+"||"+"country|like|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|like|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|like|"+employeesEntity.getExtension()+
							""+"||"+"photo|like|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|like|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|like|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|like|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND,AND"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 is(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 is(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 is(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 is(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 is(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 is(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 is(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 is(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 is(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 is(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_NotLike_Or_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|nlike|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|nlike|"+employeesEntity.getLastName()+
							""+"||"+"firstName|nlike|"+employeesEntity.getFirstName()+
							""+"||"+"title|nlike|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|nlike|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"address|nlike|"+employeesEntity.getAddress()+
							""+"||"+"city|nlike|"+employeesEntity.getCity()+
							""+"||"+"region|nlike|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|nlike|"+employeesEntity.getPostalCode()+
							""+"||"+"country|nlike|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|nlike|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|nlike|"+employeesEntity.getExtension()+
							""+"||"+"photo|nlike|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|nlike|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|nlike|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|nlike|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 not(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 not(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 not(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 not(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 not(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 not(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 not(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 not(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 not(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 not(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 not(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 not(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 not(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 not(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 not(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 not(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 not(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 not(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortAsc_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							""+"||"+"title|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eq|"+employeesEntity.getAddress()+
							""+"||"+"city|eq|"+employeesEntity.getCity()+
							""+"||"+"region|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eq|"+employeesEntity.getExtension()+
							""+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","employeeId,lastName,firstName,title,titleOfCourtesy,birthDate,hireDate,address,city,region,postalCode,country,homePhone,extension,photo,notes,reportsTo,photoPath"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 is(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 is(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 is(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 is(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 is(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 is(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 is(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 is(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 is(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 is(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterAllFields_Equals_Or_SortDesc_shouldReturnFilteredEmployees() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							""+"||"+"title|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eq|"+employeesEntity.getAddress()+
							""+"||"+"city|eq|"+employeesEntity.getCity()+
							""+"||"+"region|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eq|"+employeesEntity.getExtension()+
							""+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","-employeeId,-lastName,-firstName,-title,-titleOfCourtesy,-birthDate,-hireDate,-address,-city,-region,-postalCode,-country,-homePhone,-extension,-photo,-notes,-reportsTo,-photoPath"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_OK)
	        .body(
				"employees[0].employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeeId",
				 is(employeesEntity.getEmployeeId()),
				"employees[0].lastName",
				 Matchers.notNullValue(),
				"employees[0].lastName",
				 is(employeesEntity.getLastName()),
				"employees[0].firstName",
				 Matchers.notNullValue(),
				"employees[0].firstName",
				 is(employeesEntity.getFirstName()),
				"employees[0].title",
				 Matchers.notNullValue(),
				"employees[0].title",
				 is(employeesEntity.getTitle()),
				"employees[0].titleOfCourtesy",
				 Matchers.notNullValue(),
				"employees[0].titleOfCourtesy",
				 is(employeesEntity.getTitleOfCourtesy()),
				"employees[0].birthDate",
				 Matchers.notNullValue(),
				"employees[0].birthDate",
				 is(employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].hireDate",
				 Matchers.notNullValue(),
				"employees[0].hireDate",
				 is(employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))),
				"employees[0].address",
				 Matchers.notNullValue(),
				"employees[0].address",
				 is(employeesEntity.getAddress()),
				"employees[0].city",
				 Matchers.notNullValue(),
				"employees[0].city",
				 is(employeesEntity.getCity()),
				"employees[0].region",
				 Matchers.notNullValue(),
				"employees[0].region",
				 is(employeesEntity.getRegion()),
				"employees[0].postalCode",
				 Matchers.notNullValue(),
				"employees[0].postalCode",
				 is(employeesEntity.getPostalCode()),
				"employees[0].country",
				 Matchers.notNullValue(),
				"employees[0].country",
				 is(employeesEntity.getCountry()),
				"employees[0].homePhone",
				 Matchers.notNullValue(),
				"employees[0].homePhone",
				 is(employeesEntity.getHomePhone()),
				"employees[0].extension",
				 Matchers.notNullValue(),
				"employees[0].extension",
				 is(employeesEntity.getExtension()),
				"employees[0].photo",
				 Matchers.notNullValue(),
				"employees[0].photo",
				 is(Base64.getEncoder().encodeToString(employeesEntity.getPhoto())),
				"employees[0].notes",
				 Matchers.notNullValue(),
				"employees[0].notes",
				 is(employeesEntity.getNotes()),
				"employees[0].employeesReportsToResponse.employeeId",
				 Matchers.notNullValue(),
				"employees[0].employeesReportsToResponse.employeeId",
				 is(employeesEntity.getEmployeesReportsTo().getEmployeeId()),
				"employees[0].photoPath",
				 Matchers.notNullValue(),
				"employees[0].photoPath",
				 is(employeesEntity.getPhotoPath())
				);
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterWithInvalidFields_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeIdXXX|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastNameXXX|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstNameXXX|eq|"+employeesEntity.getFirstName()+
							""+"||"+"titleXXX|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesyXXX|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDateXXX|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDateXXX|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"addressXXX|eq|"+employeesEntity.getAddress()+
							""+"||"+"cityXXX|eq|"+employeesEntity.getCity()+
							""+"||"+"regionXXX|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCodeXXX|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"countryXXX|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhoneXXX|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extensionXXX|eq|"+employeesEntity.getExtension()+
							""+"||"+"photoXXX|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notesXXX|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsToXXX|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPathXXX|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","employeeId,lastName,firstName,title,titleOfCourtesy,birthDate,hireDate,address,city,region,postalCode,country,homePhone,extension,photo,notes,reportsTo,photoPath"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterWithInvalidOperator_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eqXXX|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eqXXX|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eqXXX|"+employeesEntity.getFirstName()+
							""+"||"+"title|eqXXX|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eqXXX|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eqXXX|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eqXXX|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eqXXX|"+employeesEntity.getAddress()+
							""+"||"+"city|eqXXX|"+employeesEntity.getCity()+
							""+"||"+"region|eqXXX|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eqXXX|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eqXXX|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eqXXX|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eqXXX|"+employeesEntity.getExtension()+
							""+"||"+"photo|eqXXX|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eqXXX|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eqXXX|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eqXXX|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","employeeId,lastName,firstName,title,titleOfCourtesy,birthDate,hireDate,address,city,region,postalCode,country,homePhone,extension,photo,notes,reportsTo,photoPath"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter operator not valid"));
	        
				
	}
	
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterWithInvalidConjunction_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							""+"||"+"title|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eq|"+employeesEntity.getAddress()+
							""+"||"+"city|eq|"+employeesEntity.getCity()+
							""+"||"+"region|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eq|"+employeesEntity.getExtension()+
							""+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX,ORXX"
	        )
	        .queryParam("sort","employeeId,lastName,firstName,title,titleOfCourtesy,birthDate,hireDate,address,city,region,postalCode,country,homePhone,extension,photo,notes,reportsTo,photoPath"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter conjunction not valid"));
	        
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterWithInvalidSort_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							""+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							""+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							""+"||"+"title|eq|"+employeesEntity.getTitle()+
							""+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							""+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							""+"||"+"address|eq|"+employeesEntity.getAddress()+
							""+"||"+"city|eq|"+employeesEntity.getCity()+
							""+"||"+"region|eq|"+employeesEntity.getRegion()+
							""+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							""+"||"+"country|eq|"+employeesEntity.getCountry()+
							""+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							""+"||"+"extension|eq|"+employeesEntity.getExtension()+
							""+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							""+"||"+"notes|eq|"+employeesEntity.getNotes()+
							""+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							""+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							""

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","employeeIdXXX,lastNameXXX,firstNameXXX,titleXXX,titleOfCourtesyXXX,birthDateXXX,hireDateXXX,addressXXX,cityXXX,regionXXX,postalCodeXXX,countryXXX,homePhoneXXX,extensionXXX,photoXXX,notesXXX,reportsToXXX,photoPathXXX"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter or sort field not valid"));
	        
				
	}
	
	@Test
	public void given10Employees_whenExecuteFindEmployeesByFilter_WithOffset0AndLimit10_FilterWithInvalidValue_thenReturnErrorsWith422Code() {
		final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "password");
	    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);
	    
	    final var employeesEntity = createEmployees("");
	    for (int i = 0; i < 9; i++) {
	    	createEmployees(String.valueOf(i));
		}
		
	    given()
	        .contentType(MediaType.APPLICATION_JSON)
	        .header(AUTHORIZATION_HEADER, authorizationHeader)
	        .queryParam("offset", 0)
	        .queryParam("limit", 10)
	        .queryParam("filter", "employeeId|eq|"+employeesEntity.getEmployeeId()+
							";:')"+"||"+"lastName|eq|"+employeesEntity.getLastName()+
							";:')"+"||"+"firstName|eq|"+employeesEntity.getFirstName()+
							";:')"+"||"+"title|eq|"+employeesEntity.getTitle()+
							";:')"+"||"+"titleOfCourtesy|eq|"+employeesEntity.getTitleOfCourtesy()+
							";:')"+"||"+"birthDate|eq|"+employeesEntity.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"hireDate|eq|"+employeesEntity.getHireDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))+
							";:')"+"||"+"address|eq|"+employeesEntity.getAddress()+
							";:')"+"||"+"city|eq|"+employeesEntity.getCity()+
							";:')"+"||"+"region|eq|"+employeesEntity.getRegion()+
							";:')"+"||"+"postalCode|eq|"+employeesEntity.getPostalCode()+
							";:')"+"||"+"country|eq|"+employeesEntity.getCountry()+
							";:')"+"||"+"homePhone|eq|"+employeesEntity.getHomePhone()+
							";:')"+"||"+"extension|eq|"+employeesEntity.getExtension()+
							";:')"+"||"+"photo|eq|"+Base64.getEncoder().encodeToString(employeesEntity.getPhoto())+
							";:')"+"||"+"notes|eq|"+employeesEntity.getNotes()+
							";:')"+"||"+"reportsTo|eq|"+employeesEntity.getEmployeesReportsTo().getEmployeeId()+
							";:')"+"||"+"photoPath|eq|"+employeesEntity.getPhotoPath()+
							";:')"

	        )  
	        .queryParam("conjunctions","OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR,OR"
	        )
	        .queryParam("sort","employeeId,lastName,firstName,title,titleOfCourtesy,birthDate,hireDate,address,city,region,postalCode,country,homePhone,extension,photo,notes,reportsTo,photoPath"
	        )
	        .get(EMPLOYEES_RESOURCE_PATH)
	        .then()
	        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
	        .body("errors.body", hasItems("filter value not valid"));
	        
				
	}
}
