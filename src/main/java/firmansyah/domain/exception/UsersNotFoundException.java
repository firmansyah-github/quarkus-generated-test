// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

public class UsersNotFoundException extends BusinessException {

	public UsersNotFoundException() {
		super(5, "users not found");
	}
}
