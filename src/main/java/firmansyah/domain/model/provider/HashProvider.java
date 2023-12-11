// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.model.provider;

public interface HashProvider {
  	String hashPassword(String password);

  	boolean checkPassword(String plaintext, String hashed);
}
