// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class UsersAlreadyExistsException extends BusinessException {

	public UsersAlreadyExistsException() {
		super(2, "users already exists");
	}
}
