// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

public class UsersAlreadyExistsException extends BusinessException {

	public UsersAlreadyExistsException() {
		super(2, "users already exists");
	}
}
