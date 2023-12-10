// created by the factor : Dec 11, 2023, 5:57:49 AM  
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
