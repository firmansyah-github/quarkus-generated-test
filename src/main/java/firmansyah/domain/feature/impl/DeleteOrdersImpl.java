// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteOrders;
import firmansyah.domain.model.orders.OrdersRepository;


@AllArgsConstructor
public class DeleteOrdersImpl implements DeleteOrders {

	private final OrdersRepository ordersRepository;

  	@Override
	public boolean handle(Integer orderId) {
		return ordersRepository.delete(orderId);
	}
}
