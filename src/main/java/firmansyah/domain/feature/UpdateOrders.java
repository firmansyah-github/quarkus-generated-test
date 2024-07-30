// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.orders.UpdateOrdersInput;


public interface UpdateOrders {
	Orders handle(UpdateOrdersInput updateOrdersInput);
}
