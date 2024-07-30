// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateOrderDetails;
import firmansyah.domain.model.orderDetails.*;
import firmansyah.domain.exception.OrderDetailsAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateOrderDetailsImpl implements CreateOrderDetails {

	private final OrderDetailsRepository orderDetailsRepository;
	private final OrderDetailsModelBuilder orderDetailsBuilder;
	private final FindOrdersByPrimaryKey findOrdersOrderIdByPrimaryKey;
	private final FindProductsByPrimaryKey findProductsProductIdByPrimaryKey;
	

	@Override
	public OrderDetails handle(NewOrderDetailsInput newOrderDetailsInput) {
		final var orderDetails =
			orderDetailsBuilder.build(newOrderDetailsInput.getUnitPrice(),
					newOrderDetailsInput.getQuantity(),
					newOrderDetailsInput.getDiscount(),
					findOrdersOrderIdByPrimaryKey.handle(newOrderDetailsInput.getOrderId()),
					findProductsProductIdByPrimaryKey.handle(newOrderDetailsInput.getProductId()));
		
		if(orderDetailsRepository.findOrderDetailsByPrimaryKey(orderDetails.getOrdersOrderId().getOrderId(), orderDetails.getProductsProductId().getProductId()).isPresent()) {
			throw new OrderDetailsAlreadyExistsException();
		} else {
			orderDetailsRepository.save(orderDetails);
		}
   
		return orderDetails;
	}
}
