// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteCustomers;
import firmansyah.domain.model.customers.CustomersRepository;


@AllArgsConstructor
public class DeleteCustomersImpl implements DeleteCustomers {

	private final CustomersRepository customersRepository;

  	@Override
	public boolean handle(String customerId) {
		return customersRepository.delete(customerId);
	}
}
