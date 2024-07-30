// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCustomerCustomerDemoByFilter;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindCustomerCustomerDemoByFilterImpl implements FindCustomerCustomerDemoByFilter {

	private final CustomerCustomerDemoRepository customerCustomerDemoRepository;

	@Override
	public PageResult<CustomerCustomerDemo> handle(ResourceFilter resourceFilterr) {
		return customerCustomerDemoRepository.findCustomerCustomerDemoByFilter(resourceFilterr);
	}
}
