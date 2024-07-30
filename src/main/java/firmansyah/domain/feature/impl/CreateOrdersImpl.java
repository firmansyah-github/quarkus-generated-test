// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateOrders;
import firmansyah.domain.model.orders.*;
import firmansyah.domain.exception.OrdersAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateOrdersImpl implements CreateOrders {

	private final OrdersRepository ordersRepository;
	private final OrdersModelBuilder ordersBuilder;
	private final FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey;
	private final FindShippersByPrimaryKey findShippersShipViaByPrimaryKey;
	private final FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey;
	

	@Override
	public Orders handle(NewOrdersInput newOrdersInput) {
		final var orders =
			ordersBuilder.build(newOrdersInput.getOrderId(),
					newOrdersInput.getOrderDate(),
					newOrdersInput.getRequiredDate(),
					newOrdersInput.getShippedDate(),
					newOrdersInput.getFreight(),
					newOrdersInput.getShipName(),
					newOrdersInput.getShipAddress(),
					newOrdersInput.getShipCity(),
					newOrdersInput.getShipRegion(),
					newOrdersInput.getShipPostalCode(),
					newOrdersInput.getShipCountry(),
					null,
					findEmployeesEmployeeIdByPrimaryKey.handle(newOrdersInput.getEmployeeId()),
					findShippersShipViaByPrimaryKey.handle(newOrdersInput.getShipVia()),
					findCustomersCustomerIdByPrimaryKey.handle(newOrdersInput.getCustomerId()));
		
		if(ordersRepository.findOrdersByPrimaryKey(orders.getOrderId()).isPresent()) {
			throw new OrdersAlreadyExistsException();
		} else {
			ordersRepository.save(orders);
		}
   
		return orders;
	}
}
