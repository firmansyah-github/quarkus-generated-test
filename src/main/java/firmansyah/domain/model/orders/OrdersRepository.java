// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orders;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface OrdersRepository {

	void save(Orders orders);

	Optional<Orders> findOrdersByPrimaryKey(Integer orderId);

	void update(Orders orders);

	boolean delete(Integer orderId);

    PageResult<Orders> findOrdersByFilter(ResourceFilter resourceFilter);
    
	long countOrders();
}
