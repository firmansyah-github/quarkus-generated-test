// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class SuppliersAlreadyExistsException extends BusinessException {

	public SuppliersAlreadyExistsException() {
		super(2, "suppliers already exists");
	}
}
