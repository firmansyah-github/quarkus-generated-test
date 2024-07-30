// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.web.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import firmansyah.infrastructure.web.exception.UnauthorizedException;
import firmansyah.infrastructure.web.provider.TokenProvider;
import firmansyah.infrastructure.web.security.annotation.Secured;
import firmansyah.infrastructure.web.security.context.DecodedJWTSecurityContext;
import firmansyah.infrastructure.web.security.context.EmptySecurityContext;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private final String AUTHORIZATION_HEADER_PREFIX = "Token ";

	@Inject
	TokenProvider tokenProvider;

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {

		String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX)) {

			String token = authorizationHeader.replace(AUTHORIZATION_HEADER_PREFIX, "");

			try {

				DecodedJWT decodedJWT = tokenProvider.verify(token);

				containerRequestContext.setSecurityContext(new DecodedJWTSecurityContext(decodedJWT, tokenProvider));

			} catch (JWTVerificationException ex) {
				containerRequestContext.abortWith(Response.ok(Response.Status.UNAUTHORIZED.toString())
						.status(Response.Status.UNAUTHORIZED).build());
			}
		} else {

			containerRequestContext.setSecurityContext(new EmptySecurityContext());

			if (!isSecurityOptional()) {
				throw new UnauthorizedException();
			}
		}
	}

	private boolean isSecurityOptional() {

		Class<?> resourceClass = resourceInfo.getResourceClass();
		Method resourceMethod = resourceInfo.getResourceMethod();
		Secured resourceClassSecured = resourceClass.getAnnotation(Secured.class);
		Secured resourceMethodSecured = resourceMethod.getAnnotation(Secured.class);

		if (resourceMethodSecured != null) {
			return resourceMethodSecured.optional();
		}

		if (resourceClassSecured != null) {
			return resourceClassSecured.optional();
		}

		return false;
	}
}
