// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class OrdersNotFoundException extends BusinessException {

	public OrdersNotFoundException() {
		super(5, "orders not found");
	}
}
