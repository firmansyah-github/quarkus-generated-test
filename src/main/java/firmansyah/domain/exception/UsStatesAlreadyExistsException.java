// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class UsStatesAlreadyExistsException extends BusinessException {

	public UsStatesAlreadyExistsException() {
		super(2, "usstates already exists");
	}
}
