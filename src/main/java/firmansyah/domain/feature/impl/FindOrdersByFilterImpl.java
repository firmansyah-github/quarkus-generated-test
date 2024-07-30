// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindOrdersByFilter;
import firmansyah.domain.model.orders.Orders;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.orders.OrdersRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindOrdersByFilterImpl implements FindOrdersByFilter {

	private final OrdersRepository ordersRepository;

	@Override
	public PageResult<Orders> handle(ResourceFilter resourceFilterr) {
		return ordersRepository.findOrdersByFilter(resourceFilterr);
	}
}
