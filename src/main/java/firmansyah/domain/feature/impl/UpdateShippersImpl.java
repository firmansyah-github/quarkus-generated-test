// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.shippers.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateShippersImpl implements UpdateShippers {

	private final ShippersRepository shippersRepository;
	private final ShippersModelBuilder shippersBuilder;
	private final FindShippersByPrimaryKey findShippersByPrimaryKey;
	

	@Override
	public Shippers handle(UpdateShippersInput updateShippersInput) {
		var shippers = findShippersByPrimaryKey.handle(updateShippersInput.getShipperId());
		
    	shippers =
			shippersBuilder.build(updateShippersInput.getShipperId(),
					updateShippersInput.getCompanyName(),
					updateShippersInput.getPhone(),
					null);
		shippersRepository.update(shippers);
    
		return shippers;
	}
}
