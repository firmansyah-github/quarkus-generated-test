// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.infrastructure.web.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import firmansyah.infrastructure.web.security.profile.Role;

public interface TokenProvider {

    String createUserToken(String subject);

	DecodedJWT verify(String token);

	Role[] extractRoles(DecodedJWT decodedJWT);
}
