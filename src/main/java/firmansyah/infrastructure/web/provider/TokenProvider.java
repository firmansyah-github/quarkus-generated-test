// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.infrastructure.web.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import firmansyah.infrastructure.web.security.profile.Role;

public interface TokenProvider {

    String createUserToken(String subject);

	DecodedJWT verify(String token);

	Role[] extractRoles(DecodedJWT decodedJWT);
}
