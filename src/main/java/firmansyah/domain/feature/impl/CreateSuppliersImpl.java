// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateSuppliers;
import firmansyah.domain.model.suppliers.*;
import firmansyah.domain.exception.SuppliersAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateSuppliersImpl implements CreateSuppliers {

	private final SuppliersRepository suppliersRepository;
	private final SuppliersModelBuilder suppliersBuilder;
	

	@Override
	public Suppliers handle(NewSuppliersInput newSuppliersInput) {
		final var suppliers =
			suppliersBuilder.build(newSuppliersInput.getSupplierId(),
					newSuppliersInput.getCompanyName(),
					newSuppliersInput.getContactName(),
					newSuppliersInput.getContactTitle(),
					newSuppliersInput.getAddress(),
					newSuppliersInput.getCity(),
					newSuppliersInput.getRegion(),
					newSuppliersInput.getPostalCode(),
					newSuppliersInput.getCountry(),
					newSuppliersInput.getPhone(),
					newSuppliersInput.getFax(),
					newSuppliersInput.getHomepage(),
					null);
		
		if(suppliersRepository.findSuppliersByPrimaryKey(suppliers.getSupplierId()).isPresent()) {
			throw new SuppliersAlreadyExistsException();
		} else {
			suppliersRepository.save(suppliers);
		}
   
		return suppliers;
	}
}
