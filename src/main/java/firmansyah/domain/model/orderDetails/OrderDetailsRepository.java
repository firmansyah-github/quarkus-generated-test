// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orderDetails;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface OrderDetailsRepository {

	void save(OrderDetails orderDetails);

	Optional<OrderDetails> findOrderDetailsByPrimaryKey(Integer orderId,Integer productId);

	void update(OrderDetails orderDetails);

	boolean delete(Integer orderId,Integer productId);

    PageResult<OrderDetails> findOrderDetailsByFilter(ResourceFilter resourceFilter);
    
	long countOrderDetails();
}
