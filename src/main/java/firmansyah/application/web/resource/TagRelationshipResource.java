// created by the factor : Feb 13, 2024, 4:07:37 PM  
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

import firmansyah.application.web.model.request.NewTagRelationshipRequest;
import firmansyah.application.web.model.request.UpdateTagRelationshipRequest;
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

@Path("/firmansyah/tagRelationship")
@AllArgsConstructor
public class TagRelationshipResource {  
  
    private final FindTagRelationshipByFilter findTagRelationshipByFilter;
  	private final FindTagRelationshipByPrimaryKey findTagRelationshipByPrimaryKey;
  	private final CreateTagRelationship createTagRelationship;
  	private final UpdateTagRelationship updateTagRelationship;
  	private final DeleteTagRelationship deleteTagRelationship;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final TagRelationshipResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findTagRelationshipByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var tagRelationshipPageResult = findTagRelationshipByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.tagRelationshipResponse(tagRelationshipPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findTagRelationshipByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("articleId") String articleId,
			@QueryParam("tagId") String tagId) {
    	final var tagRelationship = findTagRelationshipByPrimaryKey.handle(articleId, tagId);
    	
    	return Response.ok(resourceUtils.tagRelationshipResponse(tagRelationship)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewTagRelationshipRequest newTagRelationshipRequest,
      		@Context SecurityContext securityContext) {
    final var tagRelationship = createTagRelationship.handle(newTagRelationshipRequest.toNewTagRelationshipInput());
    return Response.ok(resourceUtils.tagRelationshipResponse(tagRelationship))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateTagRelationshipRequest updateTagRelationshipRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedTagRelationship =
        	updateTagRelationship.handle(updateTagRelationshipRequest.toUpdateTagRelationshipInput());
    	return Response.ok(resourceUtils.tagRelationshipResponse(updatedTagRelationship))
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
			@QueryParam("tagId") String tagId) {
		
		if (deleteTagRelationship.handle(articleId, tagId)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
