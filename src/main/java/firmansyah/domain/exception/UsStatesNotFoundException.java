// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class UsStatesNotFoundException extends BusinessException {

	public UsStatesNotFoundException() {
		super(5, "usStates not found");
	}
}
