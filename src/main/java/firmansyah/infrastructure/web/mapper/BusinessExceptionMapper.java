// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.infrastructure.web.mapper;

import firmansyah.application.web.model.response.ErrorResponse;
import firmansyah.domain.exception.*;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private final Map<Class<? extends BusinessException>, Function<BusinessException, Response>> exceptionMapper;

	public BusinessExceptionMapper() {
		this.exceptionMapper = configureExceptionMapper();
	}

	private Map<Class<? extends BusinessException>, Function<BusinessException, Response>> configureExceptionMapper() {

		final var handlerMap = new HashMap<Class<? extends BusinessException>, Function<BusinessException, Response>>();
		handlerMap.put(ArticlesNotFoundException.class, this::notFound);
		handlerMap.put(ArticlesAlreadyExistsException.class, this::conflict);
		handlerMap.put(CommentsNotFoundException.class, this::notFound);
		handlerMap.put(CommentsAlreadyExistsException.class, this::conflict);
		handlerMap.put(FavoriteRelationshipNotFoundException.class, this::notFound);
		handlerMap.put(FavoriteRelationshipAlreadyExistsException.class, this::conflict);
		handlerMap.put(FollowRelationshipNotFoundException.class, this::notFound);
		handlerMap.put(FollowRelationshipAlreadyExistsException.class, this::conflict);
		handlerMap.put(TagRelationshipNotFoundException.class, this::notFound);
		handlerMap.put(TagRelationshipAlreadyExistsException.class, this::conflict);
		handlerMap.put(TagsNotFoundException.class, this::notFound);
		handlerMap.put(TagsAlreadyExistsException.class, this::conflict);
		handlerMap.put(UsersNotFoundException.class, this::notFound);
		handlerMap.put(UsersAlreadyExistsException.class, this::conflict);
		
		handlerMap.put(ModelValidationException.class, this::unprocessableEntity);
		handlerMap.put(FilterConjunctionNotValidException.class, this::unprocessableEntity);
		handlerMap.put(FilterFieldNotValidException.class, this::unprocessableEntity);
		handlerMap.put(FilterOperatorNotValidException.class, this::unprocessableEntity);
		handlerMap.put(FilterValueNotValidException.class, this::unprocessableEntity);

		return handlerMap;
	}

	private Response notFound(BusinessException businessException) {
		return Response.ok(errorResponse(businessException)).status(Response.Status.NOT_FOUND.getStatusCode()).build();
	}

	private Response conflict(BusinessException businessException) {
		return Response.ok(errorResponse(businessException)).status(Response.Status.CONFLICT.getStatusCode()).build();
	}

	private Response unauthorized(BusinessException businessException) {
		return Response.ok(errorResponse(businessException)).status(Response.Status.UNAUTHORIZED.getStatusCode())
				.build();
	}

	private Response unprocessableEntity(BusinessException businessException) {
		return Response.ok(errorResponse(businessException)).status(422).build();
	}

	private ErrorResponse errorResponse(BusinessException businessException) {
		return new ErrorResponse(businessException.getMessages());
	}

	@Override
	public Response toResponse(BusinessException businessException) {
		return this.exceptionMapper.get(businessException.getClass()).apply(businessException);
	}
}
