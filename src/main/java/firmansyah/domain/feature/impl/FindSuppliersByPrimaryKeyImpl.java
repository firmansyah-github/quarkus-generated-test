// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindSuppliersByPrimaryKey;
import firmansyah.domain.exception.SuppliersNotFoundException;
import firmansyah.domain.model.suppliers.Suppliers;
import firmansyah.domain.model.suppliers.SuppliersRepository;


@AllArgsConstructor
public class FindSuppliersByPrimaryKeyImpl implements FindSuppliersByPrimaryKey {

	private final SuppliersRepository suppliersRepository;

	@Override
	public Suppliers handle(Integer supplierId) {
		return suppliersRepository.findSuppliersByPrimaryKey(supplierId)
    			.orElseThrow(SuppliersNotFoundException::new);
	}
}
