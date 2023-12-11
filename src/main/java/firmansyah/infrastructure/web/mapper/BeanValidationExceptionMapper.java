// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.infrastructure.web.mapper;

import firmansyah.application.web.model.response.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BeanValidationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

  	@Override
	public Response toResponse(ConstraintViolationException e) {

		ErrorResponse errorResponse = new ErrorResponse();

		e.getConstraintViolations().iterator().forEachRemaining(contraint -> {
			errorResponse.getBody().add(contraint.getMessage());
		});

		return Response.ok(errorResponse).status(422).build();
	}
}
