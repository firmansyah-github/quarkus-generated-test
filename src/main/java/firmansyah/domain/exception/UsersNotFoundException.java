// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class UsersNotFoundException extends BusinessException {

	public UsersNotFoundException() {
		super(5, "users not found");
	}
}
