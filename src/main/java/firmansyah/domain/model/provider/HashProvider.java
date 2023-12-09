// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.provider;

public interface HashProvider {
  	String hashPassword(String password);

  	boolean checkPassword(String plaintext, String hashed);
}
