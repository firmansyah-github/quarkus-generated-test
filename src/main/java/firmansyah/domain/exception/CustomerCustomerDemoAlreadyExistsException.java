// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CustomerCustomerDemoAlreadyExistsException extends BusinessException {

	public CustomerCustomerDemoAlreadyExistsException() {
		super(2, "customercustomerdemo already exists");
	}
}
