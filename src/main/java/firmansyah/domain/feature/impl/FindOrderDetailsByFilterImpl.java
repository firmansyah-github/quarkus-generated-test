// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindOrderDetailsByFilter;
import firmansyah.domain.model.orderDetails.OrderDetails;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.orderDetails.OrderDetailsRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindOrderDetailsByFilterImpl implements FindOrderDetailsByFilter {

	private final OrderDetailsRepository orderDetailsRepository;

	@Override
	public PageResult<OrderDetails> handle(ResourceFilter resourceFilterr) {
		return orderDetailsRepository.findOrderDetailsByFilter(resourceFilterr);
	}
}
