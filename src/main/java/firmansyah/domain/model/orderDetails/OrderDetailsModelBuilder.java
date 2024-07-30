// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orderDetails;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.products.Products;
import java.util.UUID;

@AllArgsConstructor
public class OrderDetailsModelBuilder {

	private final ModelValidator modelValidator;

	public OrderDetails build(Double unitPrice, Integer quantity, Double discount, Orders ordersOrderId, Products productsProductId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new OrderDetails(unitPrice, quantity, discount, ordersOrderId, productsProductId));
	}
  
}
