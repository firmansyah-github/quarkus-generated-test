// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindShippersByFilter;
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.shippers.ShippersRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindShippersByFilterImpl implements FindShippersByFilter {

	private final ShippersRepository shippersRepository;

	@Override
	public PageResult<Shippers> handle(ResourceFilter resourceFilterr) {
		return shippersRepository.findShippersByFilter(resourceFilterr);
	}
}
