// created by the factor : May 30, 2024, 6:48:44 AM  
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
    	try {
			Set<ConstraintViolation<T>> constraintViolations = validator.validate(model);

			if (!constraintViolations.isEmpty()) {
				final var messages =
			  		constraintViolations.stream()
			      		.map(ConstraintViolation::getMessage)
			      		.collect(Collectors.toList());
				throw new ModelValidationException(messages);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return model;
  	}
}
