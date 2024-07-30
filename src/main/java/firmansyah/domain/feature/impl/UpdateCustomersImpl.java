// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.customers.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateCustomersImpl implements UpdateCustomers {

	private final CustomersRepository customersRepository;
	private final CustomersModelBuilder customersBuilder;
	private final FindCustomersByPrimaryKey findCustomersByPrimaryKey;
	

	@Override
	public Customers handle(UpdateCustomersInput updateCustomersInput) {
		var customers = findCustomersByPrimaryKey.handle(updateCustomersInput.getCustomerId());
		
    	customers =
			customersBuilder.build(updateCustomersInput.getCustomerId(),
					updateCustomersInput.getCompanyName(),
					updateCustomersInput.getContactName(),
					updateCustomersInput.getContactTitle(),
					updateCustomersInput.getAddress(),
					updateCustomersInput.getCity(),
					updateCustomersInput.getRegion(),
					updateCustomersInput.getPostalCode(),
					updateCustomersInput.getCountry(),
					updateCustomersInput.getPhone(),
					updateCustomersInput.getFax(),
					null,
					null);
		customersRepository.update(customers);
    
		return customers;
	}
}
