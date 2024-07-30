// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateCustomers;
import firmansyah.domain.model.customers.*;
import firmansyah.domain.exception.CustomersAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateCustomersImpl implements CreateCustomers {

	private final CustomersRepository customersRepository;
	private final CustomersModelBuilder customersBuilder;
	

	@Override
	public Customers handle(NewCustomersInput newCustomersInput) {
		final var customers =
			customersBuilder.build(newCustomersInput.getCustomerId(),
					newCustomersInput.getCompanyName(),
					newCustomersInput.getContactName(),
					newCustomersInput.getContactTitle(),
					newCustomersInput.getAddress(),
					newCustomersInput.getCity(),
					newCustomersInput.getRegion(),
					newCustomersInput.getPostalCode(),
					newCustomersInput.getCountry(),
					newCustomersInput.getPhone(),
					newCustomersInput.getFax(),
					null,
					null);
		
		if(customersRepository.findCustomersByPrimaryKey(customers.getCustomerId()).isPresent()) {
			throw new CustomersAlreadyExistsException();
		} else {
			customersRepository.save(customers);
		}
   
		return customers;
	}
}
