// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orders;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;

import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.model.customers.Customers;
import java.util.UUID;

@AllArgsConstructor
public class OrdersModelBuilder {

	private final ModelValidator modelValidator;

	public Orders build(Integer orderId, LocalDateTime orderDate, LocalDateTime requiredDate, LocalDateTime shippedDate, Double freight, String shipName, String shipAddress, String shipCity, String shipRegion, String shipPostalCode, String shipCountry, List<OrderDetails> orderDetailsOrderIdList, Employees employeesEmployeeId, Shippers shippersShipVia, Customers customersCustomerId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Orders(orderId, orderDate, requiredDate, shippedDate, freight, shipName, shipAddress, shipCity, shipRegion, shipPostalCode, shipCountry, orderDetailsOrderIdList, employeesEmployeeId, shippersShipVia, customersCustomerId));
	}
  
}
