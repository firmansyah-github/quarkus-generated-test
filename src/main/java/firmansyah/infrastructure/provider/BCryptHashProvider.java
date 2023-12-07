// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
