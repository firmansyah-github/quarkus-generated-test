// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteOrderDetails;
import firmansyah.domain.model.orderDetails.OrderDetailsRepository;


@AllArgsConstructor
public class DeleteOrderDetailsImpl implements DeleteOrderDetails {

	private final OrderDetailsRepository orderDetailsRepository;

  	@Override
	public boolean handle(Integer orderId, Integer productId) {
		return orderDetailsRepository.delete(orderId, productId);
	}
}
