// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.orderDetails.OrderDetails;
import firmansyah.domain.model.orderDetails.NewOrderDetailsInput;

public interface CreateOrderDetails {
	OrderDetails handle(NewOrderDetailsInput newOrderDetailsInput);
}
