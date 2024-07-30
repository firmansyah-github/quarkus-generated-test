// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateShippers;
import firmansyah.domain.model.shippers.*;
import firmansyah.domain.exception.ShippersAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateShippersImpl implements CreateShippers {

	private final ShippersRepository shippersRepository;
	private final ShippersModelBuilder shippersBuilder;
	

	@Override
	public Shippers handle(NewShippersInput newShippersInput) {
		final var shippers =
			shippersBuilder.build(newShippersInput.getShipperId(),
					newShippersInput.getCompanyName(),
					newShippersInput.getPhone(),
					null);
		
		if(shippersRepository.findShippersByPrimaryKey(shippers.getShipperId()).isPresent()) {
			throw new ShippersAlreadyExistsException();
		} else {
			shippersRepository.save(shippers);
		}
   
		return shippers;
	}
}
