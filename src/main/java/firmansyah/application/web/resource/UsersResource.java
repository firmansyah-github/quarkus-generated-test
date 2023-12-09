// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.application.web.resource;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import firmansyah.application.web.model.request.NewUsersRequest;
import firmansyah.application.web.model.request.UpdateUsersRequest;
import firmansyah.domain.model.constants.ValidationMessages;
import firmansyah.domain.feature.*;
import firmansyah.application.web.resource.abs.FilterCondition;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.application.web.resource.abs.SortCondition;
import org.example.realworldapi.infrastructure.web.qualifiers.NoWrapRootValueObjectMapper;
import firmansyah.infrastructure.web.security.annotation.Secured;
import firmansyah.infrastructure.web.security.profile.Role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Path("/firmansyah/users")
@AllArgsConstructor
public class UsersResource {  
  
    private final FindUsersByFilter findUsersByFilter;
  	private final FindUsersByPrimaryKey findUsersByPrimaryKey;
  	private final CreateUsers createUsers;
  	private final UpdateUsers updateUsers;
  	private final DeleteUsers deleteUsers;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final UsersResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findUsersByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var usersPageResult = findUsersByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.usersResponse(usersPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findUsersByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("id") String id) {
    	final var users = findUsersByPrimaryKey.handle(id);
    	
    	return Response.ok(resourceUtils.usersResponse(users)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewUsersRequest newUsersRequest,
      		@Context SecurityContext securityContext) {
    final var users = createUsers.handle(newUsersRequest.toNewUsersInput());
    return Response.ok(resourceUtils.usersResponse(users))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateUsersRequest updateUsersRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedUsers =
        	updateUsers.handle(updateUsersRequest.toUpdateUsersInput());
    	return Response.ok(resourceUtils.usersResponse(updatedUsers))
        	.status(Response.Status.OK)
        	.build();
  	}
  	
  	@DELETE
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response delete(
		@Context SecurityContext securityContext ,
			@QueryParam("id") String id) {
		
		if (deleteUsers.handle(id)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
