// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.provider;

public interface HashProvider {
  	String hashPassword(String password);

  	boolean checkPassword(String plaintext, String hashed);
}
