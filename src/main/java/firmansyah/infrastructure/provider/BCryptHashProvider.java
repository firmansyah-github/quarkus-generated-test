// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.infrastructure.provider;

import firmansyah.domain.model.provider.HashProvider;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BCryptHashProvider implements HashProvider {

	  @Override
	  public String hashPassword(String password) {
	    return BCrypt.hashpw(password, BCrypt.gensalt());
	  }
	
	  @Override
	  public boolean checkPassword(String plaintext, String hashed) {
	    return BCrypt.checkpw(plaintext, hashed);
	  }
}
