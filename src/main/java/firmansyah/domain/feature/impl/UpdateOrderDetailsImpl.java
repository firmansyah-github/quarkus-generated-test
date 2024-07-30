// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.orderDetails.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateOrderDetailsImpl implements UpdateOrderDetails {

	private final OrderDetailsRepository orderDetailsRepository;
	private final OrderDetailsModelBuilder orderDetailsBuilder;
	private final FindOrderDetailsByPrimaryKey findOrderDetailsByPrimaryKey;
	private final FindOrdersByPrimaryKey findOrdersOrderIdByPrimaryKey;
	private final FindProductsByPrimaryKey findProductsProductIdByPrimaryKey;
	

	@Override
	public OrderDetails handle(UpdateOrderDetailsInput updateOrderDetailsInput) {
		var orderDetails = findOrderDetailsByPrimaryKey.handle(updateOrderDetailsInput.getOrderId(), updateOrderDetailsInput.getProductId());
		
    	orderDetails =
			orderDetailsBuilder.build(updateOrderDetailsInput.getUnitPrice(),
					updateOrderDetailsInput.getQuantity(),
					updateOrderDetailsInput.getDiscount(),
					findOrdersOrderIdByPrimaryKey.handle(updateOrderDetailsInput.getOrderId()),
					findProductsProductIdByPrimaryKey.handle(updateOrderDetailsInput.getProductId()));
		orderDetailsRepository.update(orderDetails);
    
		return orderDetails;
	}
}
