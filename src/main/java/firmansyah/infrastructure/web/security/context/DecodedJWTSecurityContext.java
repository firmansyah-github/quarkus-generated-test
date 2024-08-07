// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.web.security.context;

import com.auth0.jwt.interfaces.DecodedJWT;
import firmansyah.infrastructure.web.provider.TokenProvider;
import firmansyah.infrastructure.web.security.profile.Role;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

public class DecodedJWTSecurityContext implements SecurityContext {

    private final DecodedJWT decodedJWT;
	private final TokenProvider tokenProvider;

	public DecodedJWTSecurityContext(DecodedJWT decodedJWT, TokenProvider tokenProvider) {
		this.decodedJWT = decodedJWT;
		this.tokenProvider = tokenProvider;
	}

	@Override
	public Principal getUserPrincipal() {
		return decodedJWT::getSubject;
	}

	@Override
	public boolean isUserInRole(String role) {
		Role[] tokenRoles = tokenProvider.extractRoles(decodedJWT);
		for (Role tokenRole : tokenRoles) {
			if (role.equals(tokenRole.name())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}
}
