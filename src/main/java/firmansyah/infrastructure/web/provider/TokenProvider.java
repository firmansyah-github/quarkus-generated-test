// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.infrastructure.web.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import firmansyah.infrastructure.web.security.profile.Role;

public interface TokenProvider {

    String createUserToken(String subject);

	DecodedJWT verify(String token);

	Role[] extractRoles(DecodedJWT decodedJWT);
}
