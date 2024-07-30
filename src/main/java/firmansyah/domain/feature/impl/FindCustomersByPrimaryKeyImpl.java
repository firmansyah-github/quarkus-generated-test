// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCustomersByPrimaryKey;
import firmansyah.domain.exception.CustomersNotFoundException;
import firmansyah.domain.model.customers.Customers;
import firmansyah.domain.model.customers.CustomersRepository;


@AllArgsConstructor
public class FindCustomersByPrimaryKeyImpl implements FindCustomersByPrimaryKey {

	private final CustomersRepository customersRepository;

	@Override
	public Customers handle(String customerId) {
		return customersRepository.findCustomersByPrimaryKey(customerId)
    			.orElseThrow(CustomersNotFoundException::new);
	}
}
