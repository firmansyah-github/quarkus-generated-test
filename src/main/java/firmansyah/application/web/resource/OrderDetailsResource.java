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

import firmansyah.application.web.model.request.NewOrderDetailsRequest;
import firmansyah.application.web.model.request.UpdateOrderDetailsRequest;
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

@Path("/firmansyah/orderDetails")
@AllArgsConstructor
public class OrderDetailsResource {  
  
    private final FindOrderDetailsByFilter findOrderDetailsByFilter;
  	private final FindOrderDetailsByPrimaryKey findOrderDetailsByPrimaryKey;
  	private final CreateOrderDetails createOrderDetails;
  	private final UpdateOrderDetails updateOrderDetails;
  	private final DeleteOrderDetails deleteOrderDetails;
  	@NoWrapRootValueObjectMapper ObjectMapper objectMapper;
  	private final OrderDetailsResourceUtils resourceUtils;
  	
  	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Secured(optional = true)
	public Response findOrderDetailsByFilter(@QueryParam("offset") int offset, @QueryParam("limit") int limit,
			@Context SecurityContext securityContext, @QueryParam("filter") String filter,
            @QueryParam("conjunctions") String conjunctions,
            @QueryParam("sort") String sort)
			throws JsonProcessingException {
		
		List<FilterCondition> filterConditions = resourceUtils.parseFilterConditions(filter, conjunctions);
        List<SortCondition> sortConditions = resourceUtils.parseSortConditions(sort);
		final var resourceFilter = new ResourceFilter(offset, resourceUtils.getLimit(limit) ,
								filterConditions,sortConditions);
		final var orderDetailsPageResult = findOrderDetailsByFilter.handle(resourceFilter);

		return Response.ok(
            objectMapper.writeValueAsString(
                resourceUtils.orderDetailsResponse(orderDetailsPageResult)))
        .status(Response.Status.OK)
        .build();
	}
	
	@GET
	@Path("/find")
  	@Secured(optional = true)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response findOrderDetailsByPrimaryKey(@Context SecurityContext securityContext ,
			@QueryParam("orderId") Integer orderId,
			@QueryParam("productId") Integer productId) {
    	final var orderDetails = findOrderDetailsByPrimaryKey.handle(orderId, productId);
    	
    	return Response.ok(resourceUtils.orderDetailsResponse(orderDetails)).status(Response.Status.OK).build();
  	}
  	
  	@POST
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response create(
      		@Valid @NotNull(message = ValidationMessages.REQUEST_BODY_MUST_BE_NOT_NULL)
          	NewOrderDetailsRequest newOrderDetailsRequest,
      		@Context SecurityContext securityContext) {
    final var orderDetails = createOrderDetails.handle(newOrderDetailsRequest.toNewOrderDetailsInput());
    return Response.ok(resourceUtils.orderDetailsResponse(orderDetails))  
        .status(Response.Status.CREATED)
        .build();
  	}
  	
  	@PUT
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Consumes(MediaType.APPLICATION_JSON)
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response update(
      		@Valid @NotNull UpdateOrderDetailsRequest updateOrderDetailsRequest,
      		@Context SecurityContext securityContext) {
    	final var updatedOrderDetails =
        	updateOrderDetails.handle(updateOrderDetailsRequest.toUpdateOrderDetailsInput());
    	return Response.ok(resourceUtils.orderDetailsResponse(updatedOrderDetails))
        	.status(Response.Status.OK)
        	.build();
  	}
  	
  	@DELETE
  	@Transactional
  	@Secured({Role.ADMIN, Role.USER})
  	@Produces(MediaType.APPLICATION_JSON)
  	public Response delete(
		@Context SecurityContext securityContext ,
			@QueryParam("orderId") Integer orderId,
			@QueryParam("productId") Integer productId) {
		
		if (deleteOrderDetails.handle(orderId, productId)) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}


  
}
