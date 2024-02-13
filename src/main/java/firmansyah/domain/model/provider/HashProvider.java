// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.provider;

public interface HashProvider {
  	String hashPassword(String password);

  	boolean checkPassword(String plaintext, String hashed);
}
