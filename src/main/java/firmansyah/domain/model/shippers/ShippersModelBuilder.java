// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.shippers;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.orders.Orders;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class ShippersModelBuilder {

	private final ModelValidator modelValidator;

	public Shippers build(Integer shipperId, String companyName, String phone, List<Orders> ordersShipViaList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Shippers(shipperId, companyName, phone, ordersShipViaList));
	}
  
}
