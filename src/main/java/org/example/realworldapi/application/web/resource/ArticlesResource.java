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

import org.example.realworldapi.application.web.model.request.NewArticlesRequest;
import org.example.realworldapi.application.web.model.request.UpdateArticlesRequest;
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

@Path("/articles")
@AllArgsConstructor
public class ArticlesResource {  
  
    private final FindArticlesByFilter findArticlesByFilter;
  	private final FindArticlesByPrimaryKey findArticlesByPrimaryKey;
  	private final CreateArticles createArticles;
  	private final UpdateArticles updateArticles;
  	private final DeleteArticles deleteArticles;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final ArticlesResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findArticlesByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var articlesPageResult = findArticlesByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.articlesResponse(articlesPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findArticlesByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("id") String id) {
    	final var articles = findArticlesByPrimaryKey.handle(id);
    	
    	return Response.ok(resourceUtils.articlesResponse(articles)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewArticlesRequest newArticlesRequest,
      		@Context SecurityContext securityContext) {
    final var articles = createArticles.handle(newArticlesRequest.toNewArticlesInput());
    return Response.ok(resourceUtils.articlesResponse(articles))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateArticlesRequest updateArticlesRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedArticles =
        	updateArticles.handle(updateArticlesRequest.toUpdateArticlesInput());
    	return Response.ok(resourceUtils.articlesResponse(updatedArticles))
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
		
		if (deleteArticles.handle(id)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
