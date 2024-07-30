// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteCustomerCustomerDemo;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoRepository;


@AllArgsConstructor
public class DeleteCustomerCustomerDemoImpl implements DeleteCustomerCustomerDemo {

	private final CustomerCustomerDemoRepository customerCustomerDemoRepository;

  	@Override
	public boolean handle(String customerId, String customerTypeId) {
		return customerCustomerDemoRepository.delete(customerId, customerTypeId);
	}
}
