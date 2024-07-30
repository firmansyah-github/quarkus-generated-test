// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindOrderDetailsByPrimaryKey;
import firmansyah.domain.exception.OrderDetailsNotFoundException;
import firmansyah.domain.model.orderDetails.OrderDetails;
import firmansyah.domain.model.orderDetails.OrderDetailsRepository;


@AllArgsConstructor
public class FindOrderDetailsByPrimaryKeyImpl implements FindOrderDetailsByPrimaryKey {

	private final OrderDetailsRepository orderDetailsRepository;

	@Override
	public OrderDetails handle(Integer orderId, Integer productId) {
		return orderDetailsRepository.findOrderDetailsByPrimaryKey(orderId, productId)
    			.orElseThrow(OrderDetailsNotFoundException::new);
	}
}
