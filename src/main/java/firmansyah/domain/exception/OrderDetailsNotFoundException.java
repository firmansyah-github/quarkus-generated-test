// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class OrderDetailsNotFoundException extends BusinessException {

	public OrderDetailsNotFoundException() {
		super(5, "orderDetails not found");
	}
}
