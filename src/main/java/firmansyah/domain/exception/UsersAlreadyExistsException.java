// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

public class UsersAlreadyExistsException extends BusinessException {

	public UsersAlreadyExistsException() {
		super(2, "users already exists");
	}
}
