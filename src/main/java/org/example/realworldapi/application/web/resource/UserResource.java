package org.example.realworldapi.application.web.resource;

import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.example.realworldapi.application.web.model.request.LoginUserRequest;
import org.example.realworldapi.application.web.model.request.NewUserRequest;
import org.example.realworldapi.application.web.model.request.UpdateUserRequest;
import org.example.realworldapi.application.web.model.response.UserResponse;
import org.example.realworldapi.domain.exception.InvalidPasswordException;
import org.example.realworldapi.domain.exception.UserNotFoundException;
import org.example.realworldapi.domain.feature.CreateUser;
import org.example.realworldapi.domain.feature.FindUserById;
import org.example.realworldapi.domain.feature.LoginUser;
import org.example.realworldapi.domain.feature.UpdateUser;
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.infrastructure.web.exception.UnauthorizedException;
import org.example.realworldapi.infrastructure.web.provider.TokenProvider;
import org.example.realworldapi.infrastructure.web.security.annotation.Secured;
import org.example.realworldapi.infrastructure.web.security.profile.Role;

import lombok.AllArgsConstructor;

@Path("/user")
@AllArgsConstructor
public class UserResource {

    private final CreateUser createUser;
	private final LoginUser loginUser;
	private final TokenProvider tokenProvider;
	private final FindUserById findUserById;
	private final UpdateUser updateUser;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL) LoginUserRequest loginUserRequest) {
		User user;
		try {
			user = loginUser.handle(loginUserRequest.toLoginUserInput());
		} catch (UserNotFoundException | InvalidPasswordException ex) {
			throw new UnauthorizedException();
		}
		final var token = tokenProvider.createUserToken(user.getId().toString());
		return Response.ok(new UserResponse(user, token)).status(Response.Status.OK).build();
	}

	@GET
	@Secured({ Role.ADMIN, Role.USER })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@Context SecurityContext securityContext) {
		final var userId = UUID.fromString(securityContext.getUserPrincipal().getName());
		final var user = findUserById.handle(userId);
		final var token = tokenProvider.createUserToken(user.getId().toString());
		return Response.ok(new UserResponse(user, token)).status(Response.Status.OK).build();
	}

	@PUT
	@Transactional
	@Secured({ Role.ADMIN, Role.USER })
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@Context SecurityContext securityContext,
			@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL) UpdateUserRequest updateUserRequest) {
		final var userId = UUID.fromString(securityContext.getUserPrincipal().getName());
		final var user = updateUser.handle(updateUserRequest.toUpdateUserInput(userId));
		final var token = tokenProvider.createUserToken(user.getId().toString());
		return Response.ok(new UserResponse(user, token)).status(Response.Status.OK).build();
	}

	@POST
	@Transactional
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(
			@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL) NewUserRequest newUserRequest,
			@Context SecurityException context) {
		final var user = createUser.handle(newUserRequest.toNewUserInput());
		final var token = tokenProvider.createUserToken(user.getId().toString());
		return Response.ok(new UserResponse(user, token)).status(Response.Status.CREATED).build();
	}
}
