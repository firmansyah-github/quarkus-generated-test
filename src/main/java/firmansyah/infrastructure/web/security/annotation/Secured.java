// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.web.security.annotation;

import firmansyah.infrastructure.web.security.profile.Role;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Secured {
	Role[] value() default {};

	boolean optional() default false;
}
