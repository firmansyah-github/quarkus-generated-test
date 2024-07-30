// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCustomersByFilter;
import firmansyah.domain.model.customers.Customers;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.customers.CustomersRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindCustomersByFilterImpl implements FindCustomersByFilter {

	private final CustomersRepository customersRepository;

	@Override
	public PageResult<Customers> handle(ResourceFilter resourceFilterr) {
		return customersRepository.findCustomersByFilter(resourceFilterr);
	}
}
