// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindSuppliersByFilter;
import firmansyah.domain.model.suppliers.Suppliers;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.suppliers.SuppliersRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindSuppliersByFilterImpl implements FindSuppliersByFilter {

	private final SuppliersRepository suppliersRepository;

	@Override
	public PageResult<Suppliers> handle(ResourceFilter resourceFilterr) {
		return suppliersRepository.findSuppliersByFilter(resourceFilterr);
	}
}
