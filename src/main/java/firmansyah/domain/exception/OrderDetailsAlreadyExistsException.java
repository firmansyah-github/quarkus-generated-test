// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class OrderDetailsAlreadyExistsException extends BusinessException {

	public OrderDetailsAlreadyExistsException() {
		super(2, "orderdetails already exists");
	}
}
