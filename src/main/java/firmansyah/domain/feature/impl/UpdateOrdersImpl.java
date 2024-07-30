// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.orders.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateOrdersImpl implements UpdateOrders {

	private final OrdersRepository ordersRepository;
	private final OrdersModelBuilder ordersBuilder;
	private final FindOrdersByPrimaryKey findOrdersByPrimaryKey;
	private final FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey;
	private final FindShippersByPrimaryKey findShippersShipViaByPrimaryKey;
	private final FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey;
	

	@Override
	public Orders handle(UpdateOrdersInput updateOrdersInput) {
		var orders = findOrdersByPrimaryKey.handle(updateOrdersInput.getOrderId());
		
    	orders =
			ordersBuilder.build(updateOrdersInput.getOrderId(),
					updateOrdersInput.getOrderDate(),
					updateOrdersInput.getRequiredDate(),
					updateOrdersInput.getShippedDate(),
					updateOrdersInput.getFreight(),
					updateOrdersInput.getShipName(),
					updateOrdersInput.getShipAddress(),
					updateOrdersInput.getShipCity(),
					updateOrdersInput.getShipRegion(),
					updateOrdersInput.getShipPostalCode(),
					updateOrdersInput.getShipCountry(),
					null,
					findEmployeesEmployeeIdByPrimaryKey.handle(updateOrdersInput.getEmployeeId()),
					findShippersShipViaByPrimaryKey.handle(updateOrdersInput.getShipVia()),
					findCustomersCustomerIdByPrimaryKey.handle(updateOrdersInput.getCustomerId()));
		ordersRepository.update(orders);
    
		return orders;
	}
}
