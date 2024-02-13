// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.infrastructure.web.security.context;

import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

public class EmptySecurityContext implements SecurityContext {
    @Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public boolean isUserInRole(String s) {
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
