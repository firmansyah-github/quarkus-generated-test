// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class ShippersAlreadyExistsException extends BusinessException {

	public ShippersAlreadyExistsException() {
		super(2, "shippers already exists");
	}
}
