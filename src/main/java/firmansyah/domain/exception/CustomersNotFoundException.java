// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CustomersNotFoundException extends BusinessException {

	public CustomersNotFoundException() {
		super(5, "customers not found");
	}
}
