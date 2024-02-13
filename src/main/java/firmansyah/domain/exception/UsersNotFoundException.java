// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class UsersNotFoundException extends BusinessException {

	public UsersNotFoundException() {
		super(5, "users not found");
	}
}
