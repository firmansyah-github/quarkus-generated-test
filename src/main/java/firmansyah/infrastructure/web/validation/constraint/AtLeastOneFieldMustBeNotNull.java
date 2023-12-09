// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.infrastructure.web.validation.constraint;

import firmansyah.infrastructure.web.validation.validator.AtLeastOneFieldMustBeNotNullValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneFieldMustBeNotNullValidator.class)
@Documented
public @interface AtLeastOneFieldMustBeNotNull {

    String message() default "At least one field must be not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fieldNames() default {};

}
