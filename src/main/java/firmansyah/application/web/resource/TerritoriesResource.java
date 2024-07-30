// created by the factor : May 30, 2024, 6:48:44 AM  
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

import firmansyah.application.web.model.request.NewTerritoriesRequest;
import firmansyah.application.web.model.request.UpdateTerritoriesRequest;
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

@Path("/firmansyah/territories")
@AllArgsConstructor
public class TerritoriesResource {  
  
    private final FindTerritoriesByFilter findTerritoriesByFilter;
  	private final FindTerritoriesByPrimaryKey findTerritoriesByPrimaryKey;
  	private final CreateTerritories createTerritories;
  	private final UpdateTerritories updateTerritories;
  	private final DeleteTerritories deleteTerritories;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final TerritoriesResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findTerritoriesByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var territoriesPageResult = findTerritoriesByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.territoriesResponse(territoriesPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findTerritoriesByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("territoryId") String territoryId) {
    	final var territories = findTerritoriesByPrimaryKey.handle(territoryId);
    	
    	return Response.ok(resourceUtils.territoriesResponse(territories)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewTerritoriesRequest newTerritoriesRequest,
      		@Context SecurityContext securityContext) {
    final var territories = createTerritories.handle(newTerritoriesRequest.toNewTerritoriesInput());
    return Response.ok(resourceUtils.territoriesResponse(territories))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateTerritoriesRequest updateTerritoriesRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedTerritories =
        	updateTerritories.handle(updateTerritoriesRequest.toUpdateTerritoriesInput());
    	return Response.ok(resourceUtils.territoriesResponse(updatedTerritories))
        	.status(Response.Status.OK)
        	.build();
  	}
  	
  	@DELETE
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response delete(
		@Context SecurityContext securityContext ,
			@QueryParam("territoryId") String territoryId) {
		
		if (deleteTerritories.handle(territoryId)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
