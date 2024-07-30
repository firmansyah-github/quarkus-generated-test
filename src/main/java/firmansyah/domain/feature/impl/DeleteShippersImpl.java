// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteShippers;
import firmansyah.domain.model.shippers.ShippersRepository;


@AllArgsConstructor
public class DeleteShippersImpl implements DeleteShippers {

	private final ShippersRepository shippersRepository;

  	@Override
	public boolean handle(Integer shipperId) {
		return shippersRepository.delete(shipperId);
	}
}
