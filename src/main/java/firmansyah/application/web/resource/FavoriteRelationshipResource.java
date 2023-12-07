// created by the factor : Dec 7, 2023, 4:03:00 PM  
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

import firmansyah.application.web.model.request.NewFavoriteRelationshipRequest;
import firmansyah.application.web.model.request.UpdateFavoriteRelationshipRequest;
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

@Path("/firmansyah/favoriteRelationship")
@AllArgsConstructor
public class FavoriteRelationshipResource {  
  
    private final FindFavoriteRelationshipByFilter findFavoriteRelationshipByFilter;
  	private final FindFavoriteRelationshipByPrimaryKey findFavoriteRelationshipByPrimaryKey;
  	private final CreateFavoriteRelationship createFavoriteRelationship;
  	private final UpdateFavoriteRelationship updateFavoriteRelationship;
  	private final DeleteFavoriteRelationship deleteFavoriteRelationship;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final FavoriteRelationshipResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findFavoriteRelationshipByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var favoriteRelationshipPageResult = findFavoriteRelationshipByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.favoriteRelationshipResponse(favoriteRelationshipPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findFavoriteRelationshipByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("articleId") String articleId,
			@QueryParam("userId") String userId) {
    	final var favoriteRelationship = findFavoriteRelationshipByPrimaryKey.handle(articleId, userId);
    	
    	return Response.ok(resourceUtils.favoriteRelationshipResponse(favoriteRelationship)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewFavoriteRelationshipRequest newFavoriteRelationshipRequest,
      		@Context SecurityContext securityContext) {
    final var favoriteRelationship = createFavoriteRelationship.handle(newFavoriteRelationshipRequest.toNewFavoriteRelationshipInput());
    return Response.ok(resourceUtils.favoriteRelationshipResponse(favoriteRelationship))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateFavoriteRelationshipRequest updateFavoriteRelationshipRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedFavoriteRelationship =
        	updateFavoriteRelationship.handle(updateFavoriteRelationshipRequest.toUpdateFavoriteRelationshipInput());
    	return Response.ok(resourceUtils.favoriteRelationshipResponse(updatedFavoriteRelationship))
        	.status(Response.Status.OK)
        	.build();
  	}
  	
  	@DELETE
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response delete(
		@Context SecurityContext securityContext ,
			@QueryParam("articleId") String articleId,
			@QueryParam("userId") String userId) {
		
		if (deleteFavoriteRelationship.handle(articleId, userId)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
