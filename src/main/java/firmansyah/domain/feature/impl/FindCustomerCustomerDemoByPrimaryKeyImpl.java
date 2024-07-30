// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCustomerCustomerDemoByPrimaryKey;
import firmansyah.domain.exception.CustomerCustomerDemoNotFoundException;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoRepository;


@AllArgsConstructor
public class FindCustomerCustomerDemoByPrimaryKeyImpl implements FindCustomerCustomerDemoByPrimaryKey {

	private final CustomerCustomerDemoRepository customerCustomerDemoRepository;

	@Override
	public CustomerCustomerDemo handle(String customerId, String customerTypeId) {
		return customerCustomerDemoRepository.findCustomerCustomerDemoByPrimaryKey(customerId, customerTypeId)
    			.orElseThrow(CustomerCustomerDemoNotFoundException::new);
	}
}
