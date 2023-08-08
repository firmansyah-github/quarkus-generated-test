package org.example.realworldapi.integration;

import static io.restassured.RestAssured.given;
import static org.example.realworldapi.utils.TestConstants.API_PREFIX;
import static org.example.realworldapi.utils.TestConstants.AUTHORIZATION_HEADER;
import static org.example.realworldapi.utils.TestConstants.AUTHORIZATION_HEADER_VALUE_PREFIX;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.example.realworldapi.application.web.model.request.LoginUserRequest;
import org.example.realworldapi.application.web.model.request.NewUserRequest;
import org.example.realworldapi.application.web.model.request.UpdateUserRequest;
import org.example.realworldapi.utils.AbstractIntegrationTest;
import org.example.realworldapi.utils.UserEntityUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserResourceIntegrationTest extends AbstractIntegrationTest {

  private final String USER_RESOURCE_PATH = API_PREFIX + "/user";
  private final String LOGIN_PATH = USER_RESOURCE_PATH + "/login";
  
  @Test
	public void givenAValidUser_whenCallingRegisterUserEndpoint_thenReturnAnUserWithTokenFieldAndCode201()
			throws JsonProcessingException {

		NewUserRequest newUser = new NewUserRequest();		
		newUser.setId(UUID.randomUUID().toString());
		newUser.setUsername("user");
		newUser.setEmail("user@mail.com");
		newUser.setPassword("user123");
		newUser.setBio("bio123");
		newUser.setImage("image123");

		given().contentType(MediaType.APPLICATION_JSON).body(objectMapper.writeValueAsString(newUser)).when()
				.post(USER_RESOURCE_PATH+"/create").then().statusCode(HttpStatus.SC_CREATED)
				.body("user.id", Matchers.notNullValue(), "user.password",
						Matchers.nullValue(), "user.username", Matchers.notNullValue(), "user.email",
						Matchers.notNullValue(), "user.token", Matchers.notNullValue(), "user.username",
						is(newUser.getUsername()), "user.email", is(newUser.getEmail()), "user.bio", is(newUser.getBio()),
						"user.image", is(newUser.getImage()), "user.bio", Matchers.notNullValue(), "user.image",
						Matchers.notNullValue());
	}
  
  @Test
  public void
      givenAPersistedUser_whenCallingRegisterUserEndpointWithExistingEmail_thenReturnCode409()
          throws JsonProcessingException {

    String userPassword = "123";

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", userPassword);

    NewUserRequest newUser = new NewUserRequest();
    newUser.setId(UUID.randomUUID().toString());
	newUser.setUsername("user");
	newUser.setEmail(user.getEmail());
	newUser.setPassword("user123");
	newUser.setBio("bio123");
	newUser.setImage("image123");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(newUser))
        .when()
        .post(USER_RESOURCE_PATH+"/create")
        .then()
        .statusCode(HttpStatus.SC_CONFLICT)
        .body("errors.body", hasItems("email already exists"));
  }
  
  @Test
  public void
      givenAPersistedUser_whenCallingRegisterUserEndpointWithExistingUsername_thenReturnCode409()
          throws JsonProcessingException {

    String userPassword = "123";

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", userPassword);

    NewUserRequest newUser = new NewUserRequest();
    newUser.setId(UUID.randomUUID().toString());
	newUser.setUsername(user.getUsername());
	newUser.setEmail("user2@mail.com");
	newUser.setPassword("user123");
	newUser.setBio("bio123");
	newUser.setImage("image123");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(newUser))
        .when()
        .post(USER_RESOURCE_PATH+"/create")
        .then()
        .statusCode(HttpStatus.SC_CONFLICT)
        .body("errors.body", hasItems("username already exists"));
  }
  
  @Test
  public void givenAnInvalidUser_thenReturnErrorsWith422Code() throws JsonProcessingException {

    NewUserRequest newUser = new NewUserRequest();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(newUser))
        .when()
        .post(USER_RESOURCE_PATH+"/create")
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body(
            "errors.body",
            hasSize(3),
            "errors.body",
            hasItems(
            		//"id of USER must not be blank",
                "username must be not blank",
                "email must be not blank",
                "password must be not blank"));
  }
  
  @Test
  public void givenAnInvalidEmail_thenReturnErrorsWith422Code() throws JsonProcessingException {

    NewUserRequest newUser = new NewUserRequest();
    newUser.setId(UUID.randomUUID().toString());
    newUser.setUsername("username");
    newUser.setEmail("email");
    newUser.setPassword("user123");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(newUser))
        .when()
        .post(USER_RESOURCE_PATH+"/create")
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body(
            "errors.body",
            hasSize(1),
            "errors.body",
            hasItems("must be a well-formed email address"));
  }
  
  @Test
  public void givenAInvalidLogin_whenExecuteLoginEndpoint_shouldReturnErrorsWith422Code()
      throws JsonProcessingException {

    LoginUserRequest loginRequest = new LoginUserRequest();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(loginRequest))
        .when()
        .post(LOGIN_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body("errors.body", hasItems("email must be not blank", "password must be not blank"));
  }
  
  @Test
  public void givenAInvalidLoginEmail_whenExecuteLoginEndpoint_shouldReturnUnauthorized()
      throws JsonProcessingException {

    String userPassword = "123";

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", userPassword);

    LoginUserRequest loginRequest = new LoginUserRequest();
    loginRequest.setEmail("user2@mail.com");
    loginRequest.setPassword(userPassword);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(loginRequest))
        .when()
        .post(LOGIN_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED)
        .body("errors.body", hasItems("Unauthorized"));
  }
  
  @Test
  public void givenAInvalidLoginPassword_whenExecuteLoginEndpoint_shouldReturnUnauthorized()
      throws JsonProcessingException {

    final var user = createUserEntity("user1", "user1@mail.com", "123", "bio", "image");

    LoginUserRequest loginRequest = new LoginUserRequest();
    loginRequest.setEmail(user.getEmail());
    loginRequest.setPassword("145");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(loginRequest))
        .when()
        .post(LOGIN_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED)
        .body("errors.body", hasItems("Unauthorized"));
  }

  @Test
  public void givenAValidToken_whenExecuteGetUserEndpoint_shouldReturnLoggedInUser() {

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "123");

    given()
        .header(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER_VALUE_PREFIX + token(user))
        .contentType(MediaType.APPLICATION_JSON)
        .get(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(
            "user.id",
            Matchers.notNullValue(),
            "user.id",
            is(user.getId().toString()),
            "user.password",
            Matchers.nullValue(),
            "user.username",
            Matchers.notNullValue(),
            "user.email",
            Matchers.notNullValue(),
            "user.token",
            Matchers.notNullValue(),
            "user.username",
            is(user.getUsername()),
            "user.email",
            is(user.getEmail()),
            "user.bio",
            is(user.getBio()),
            "user.image",
            is(user.getImage()),
            "user.bio",
            Matchers.notNullValue(),
            "user.image",
            Matchers.notNullValue());
  }

  @Test
  public void givenAInexistentUser_whenExecuteGetUserEndpoint_shouldReturn404NotFound() {

    String authorizationHeader =
        AUTHORIZATION_HEADER_VALUE_PREFIX
            + tokenProvider.createUserToken(
                UUID.fromString("8848fc9e-38d7-4bed-95a5-90d5b8c752e7").toString());

    given()
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .contentType(MediaType.APPLICATION_JSON)
        .get(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .body("errors.body", hasItems("user not found"));
  }

  @Test
  public void
      givenARequestWithoutAuthorizationHeader_whenExecuteGetUserEndpoint_shouldReturnUnauthorized()
          throws JsonProcessingException {

    final var user = UserEntityUtils.create("user1", "user1@mail.com", "123");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectMapper.writeValueAsString(user))
        .get(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNAUTHORIZED)
        .body("errors.body", hasItems("Unauthorized"));
  }

  @Test
  public void givenAExistentUser_whenExecuteUpdateUserEndpoint_shouldReturnUpdatedUser()
      throws JsonProcessingException {

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setUsername("user1");
    updateUserRequest.setEmail("user1@mail.com");
    updateUserRequest.setBio("Bio2");
    updateUserRequest.setImage("image2");
    updateUserRequest.setId(UUID.fromString("8848fc9e-38d7-4bed-95a5-90d5b8c752e7"));

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(
        		"user.id",
                Matchers.notNullValue(),
                "user.id",
                is(user.getId().toString()),
                "user.password",
                Matchers.nullValue(),
                "user.username",
                Matchers.notNullValue(),
                "user.email",
                Matchers.notNullValue(),
                "user.token",
                Matchers.notNullValue(),
                "user.username",
                is(updateUserRequest.getUsername()),
                "user.email",
                is(updateUserRequest.getEmail()),
                "user.bio",
                is(updateUserRequest.getBio()),
                "user.image",
                is(updateUserRequest.getImage()),
                "user.bio",
                Matchers.notNullValue(),
                "user.image",
                Matchers.notNullValue());
  }

  @Test
  public void givenAExistentUser_whenExecuteUpdateUserEndpointWithEmptyBody_shouldReturn422()
      throws JsonProcessingException {

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body("errors.body", hasItems("At least one field must be not null"));
  }
  
  @Test
  public void givenAnotherExistingUsername_whenExecuteUpdateUserEndpoint_shouldReturn409()
      throws JsonProcessingException {

    final var otherUser = createUserEntity("user", "user@mail.com", "bio", "image", "123");

    final var currentUser =
        createUserEntity("currentUser", "current@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(currentUser);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setUsername(otherUser.getUsername());

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_CONFLICT)
        .body("errors.body", hasItems("username already exists"));
  }
  
  @Test
  public void givenAnotherExistingEmail_whenExecuteUpdateUserEndpoint_shouldReturn409()
      throws JsonProcessingException {

    final var otherUser = createUserEntity("user", "user@mail.com", "bio", "image", "123");

    final var currentUser =
        createUserEntity("currentUser", "current@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(currentUser);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setEmail(otherUser.getEmail());

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_CONFLICT)
        .body("errors.body", hasItems("email already exists"));
  }
  
  @Test
  public void givenAExistentUser_whenExecuteUpdateUserEndpointWithEmptyUsername_shouldReturn422()
      throws JsonProcessingException {

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setUsername("");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body("errors.body", hasItems("username must be not blank"));
  }
  
  @Test
  public void givenAExistentUser_whenExecuteUpdateUserEndpointWithInvalidEmail_shouldReturn422()
      throws JsonProcessingException {

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setEmail("email");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body(
            "errors.body",
            hasSize(1),
            "errors.body",
            hasItems("must be a well-formed email address"));
  }
  
  @Test
  public void givenAExistentUser_whenExecuteUpdateUserEndpointWithBlankUsername_shouldReturn422()
      throws JsonProcessingException {

    final var user = createUserEntity("user1", "user1@mail.com", "bio", "image", "123");

    String authorizationHeader = AUTHORIZATION_HEADER_VALUE_PREFIX + token(user);

    UpdateUserRequest updateUserRequest = new UpdateUserRequest();
    updateUserRequest.setUsername(" ");

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION_HEADER, authorizationHeader)
        .body(objectMapper.writeValueAsString(updateUserRequest))
        .put(USER_RESOURCE_PATH)
        .then()
        .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .body("errors.body", hasItems("username must be not blank"));
  }
}



