// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class OrdersAlreadyExistsException extends BusinessException {

	public OrdersAlreadyExistsException() {
		super(2, "orders already exists");
	}
}
