// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.suppliers.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateSuppliersImpl implements UpdateSuppliers {

	private final SuppliersRepository suppliersRepository;
	private final SuppliersModelBuilder suppliersBuilder;
	private final FindSuppliersByPrimaryKey findSuppliersByPrimaryKey;
	

	@Override
	public Suppliers handle(UpdateSuppliersInput updateSuppliersInput) {
		var suppliers = findSuppliersByPrimaryKey.handle(updateSuppliersInput.getSupplierId());
		
    	suppliers =
			suppliersBuilder.build(updateSuppliersInput.getSupplierId(),
					updateSuppliersInput.getCompanyName(),
					updateSuppliersInput.getContactName(),
					updateSuppliersInput.getContactTitle(),
					updateSuppliersInput.getAddress(),
					updateSuppliersInput.getCity(),
					updateSuppliersInput.getRegion(),
					updateSuppliersInput.getPostalCode(),
					updateSuppliersInput.getCountry(),
					updateSuppliersInput.getPhone(),
					updateSuppliersInput.getFax(),
					updateSuppliersInput.getHomepage(),
					null);
		suppliersRepository.update(suppliers);
    
		return suppliers;
	}
}
