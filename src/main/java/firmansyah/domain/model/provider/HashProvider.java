// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.model.provider;

public interface HashProvider {
  	String hashPassword(String password);

  	boolean checkPassword(String plaintext, String hashed);
}
