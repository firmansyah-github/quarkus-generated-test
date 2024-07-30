// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CustomersAlreadyExistsException extends BusinessException {

	public CustomersAlreadyExistsException() {
		super(2, "customers already exists");
	}
}
