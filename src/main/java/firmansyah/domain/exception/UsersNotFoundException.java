// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

public class UsersNotFoundException extends BusinessException {

	public UsersNotFoundException() {
		super(5, "users not found");
	}
}
