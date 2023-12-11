// created by the factor : Dec 11, 2023, 6:10:51 PM  
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

import firmansyah.application.web.model.request.NewTagsRequest;
import firmansyah.application.web.model.request.UpdateTagsRequest;
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

@Path("/firmansyah/tags")
@AllArgsConstructor
public class TagsResource {  
  
    private final FindTagsByFilter findTagsByFilter;
  	private final FindTagsByPrimaryKey findTagsByPrimaryKey;
  	private final CreateTags createTags;
  	private final UpdateTags updateTags;
  	private final DeleteTags deleteTags;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final TagsResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findTagsByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var tagsPageResult = findTagsByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.tagsResponse(tagsPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findTagsByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("id") String id) {
    	final var tags = findTagsByPrimaryKey.handle(id);
    	
    	return Response.ok(resourceUtils.tagsResponse(tags)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewTagsRequest newTagsRequest,
      		@Context SecurityContext securityContext) {
    final var tags = createTags.handle(newTagsRequest.toNewTagsInput());
    return Response.ok(resourceUtils.tagsResponse(tags))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateTagsRequest updateTagsRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedTags =
        	updateTags.handle(updateTagsRequest.toUpdateTagsInput());
    	return Response.ok(resourceUtils.tagsResponse(updatedTags))
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
		
		if (deleteTags.handle(id)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
