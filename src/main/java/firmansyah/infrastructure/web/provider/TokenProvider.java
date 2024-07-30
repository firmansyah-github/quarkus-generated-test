// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.web.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import firmansyah.infrastructure.web.security.profile.Role;

public interface TokenProvider {

    String createUserToken(String subject);

	DecodedJWT verify(String token);

	Role[] extractRoles(DecodedJWT decodedJWT);
}
