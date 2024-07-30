// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.orders.Orders;
            

import java.time.LocalDateTime;





public interface FindOrdersByPrimaryKey {
	Orders handle(Integer orderId);
}

