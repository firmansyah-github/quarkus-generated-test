// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class UsersNotFoundException extends BusinessException {

	public UsersNotFoundException() {
		super(5, "users not found");
	}
}
