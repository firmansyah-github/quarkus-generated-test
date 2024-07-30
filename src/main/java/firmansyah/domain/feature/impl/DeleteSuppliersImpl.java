// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteSuppliers;
import firmansyah.domain.model.suppliers.SuppliersRepository;


@AllArgsConstructor
public class DeleteSuppliersImpl implements DeleteSuppliers {

	private final SuppliersRepository suppliersRepository;

  	@Override
	public boolean handle(Integer supplierId) {
		return suppliersRepository.delete(supplierId);
	}
}
