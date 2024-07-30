// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindOrdersByPrimaryKey;
import firmansyah.domain.exception.OrdersNotFoundException;
import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.orders.OrdersRepository;


@AllArgsConstructor
public class FindOrdersByPrimaryKeyImpl implements FindOrdersByPrimaryKey {

	private final OrdersRepository ordersRepository;

	@Override
	public Orders handle(Integer orderId) {
		return ordersRepository.findOrdersByPrimaryKey(orderId)
    			.orElseThrow(OrdersNotFoundException::new);
	}
}
