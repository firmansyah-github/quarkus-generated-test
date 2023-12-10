// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class UsersAlreadyExistsException extends BusinessException {

	public UsersAlreadyExistsException() {
		super(2, "users already exists");
	}
}
