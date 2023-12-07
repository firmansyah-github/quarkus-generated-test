// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.exception;

public class UsersAlreadyExistsException extends BusinessException {

	public UsersAlreadyExistsException() {
		super(2, "users already exists");
	}
}
