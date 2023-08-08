package org.example.realworldapi.application.web.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.example.realworldapi.application.web.model.request.NewFollowRelationshipRequest;
import org.example.realworldapi.application.web.model.request.UpdateFollowRelationshipRequest;
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.application.web.resource.abs.FilterCondition;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.application.web.resource.abs.SortCondition;
import org.example.realworldapi.infrastructure.web.qualifiers.NoWrapRootValueObjectMapper;
import org.example.realworldapi.infrastructure.web.security.annotation.Secured;
import org.example.realworldapi.infrastructure.web.security.profile.Role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Path("/followRelationship")
@AllArgsConstructor
public class FollowRelationshipResource {  
  
    private final FindFollowRelationshipByFilter findFollowRelationshipByFilter;
  	private final FindFollowRelationshipByPrimaryKey findFollowRelationshipByPrimaryKey;
  	private final CreateFollowRelationship createFollowRelationship;
  	private final UpdateFollowRelationship updateFollowRelationship;
  	private final DeleteFollowRelationship deleteFollowRelationship;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final FollowRelationshipResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findFollowRelationshipByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var followRelationshipPageResult = findFollowRelationshipByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.followRelationshipResponse(followRelationshipPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findFollowRelationshipByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("followedId") String followedId,
			@QueryParam("userId") String userId) {
    	final var followRelationship = findFollowRelationshipByPrimaryKey.handle(followedId, userId);
    	
    	return Response.ok(resourceUtils.followRelationshipResponse(followRelationship)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewFollowRelationshipRequest newFollowRelationshipRequest,
      		@Context SecurityContext securityContext) {
    final var followRelationship = createFollowRelationship.handle(newFollowRelationshipRequest.toNewFollowRelationshipInput());
    return Response.ok(resourceUtils.followRelationshipResponse(followRelationship))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateFollowRelationshipRequest updateFollowRelationshipRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedFollowRelationship =
        	updateFollowRelationship.handle(updateFollowRelationshipRequest.toUpdateFollowRelationshipInput());
    	return Response.ok(resourceUtils.followRelationshipResponse(updatedFollowRelationship))
        	.status(Response.Status.OK)
        	.build();
  	}
  	
  	@DELETE
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response delete(
		@Context SecurityContext securityContext ,
			@QueryParam("followedId") String followedId,
			@QueryParam("userId") String userId) {
		
		if (deleteFollowRelationship.handle(followedId, userId)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
