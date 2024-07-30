// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindShippersByPrimaryKey;
import firmansyah.domain.exception.ShippersNotFoundException;
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.model.shippers.ShippersRepository;


@AllArgsConstructor
public class FindShippersByPrimaryKeyImpl implements FindShippersByPrimaryKey {

	private final ShippersRepository shippersRepository;

	@Override
	public Shippers handle(Integer shipperId) {
		return shippersRepository.findShippersByPrimaryKey(shipperId)
    			.orElseThrow(ShippersNotFoundException::new);
	}
}
