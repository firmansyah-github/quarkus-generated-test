// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.validator;

import lombok.AllArgsConstructor;
import firmansyah.domain.exception.ModelValidationException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@ApplicationScoped
public class ModelValidator {

  	private final Validator validator;

  	public <T> T validate(T model) {
    	Set<ConstraintViolation<T>> constraintViolations = validator.validate(model);

    	if (!constraintViolations.isEmpty()) {
      		final var messages =
          		constraintViolations.stream()
              		.map(ConstraintViolation::getMessage)
              		.collect(Collectors.toList());
      		throw new ModelValidationException(messages);
    	}

    	return model;
  	}
}
