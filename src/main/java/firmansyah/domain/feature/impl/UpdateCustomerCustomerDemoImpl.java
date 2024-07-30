// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.customerCustomerDemo.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateCustomerCustomerDemoImpl implements UpdateCustomerCustomerDemo {

	private final CustomerCustomerDemoRepository customerCustomerDemoRepository;
	private final CustomerCustomerDemoModelBuilder customerCustomerDemoBuilder;
	private final FindCustomerCustomerDemoByPrimaryKey findCustomerCustomerDemoByPrimaryKey;
	private final FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey;
	private final FindCustomerDemographicsByPrimaryKey findCustomerDemographicsCustomerTypeIdByPrimaryKey;
	

	@Override
	public CustomerCustomerDemo handle(UpdateCustomerCustomerDemoInput updateCustomerCustomerDemoInput) {
		var customerCustomerDemo = findCustomerCustomerDemoByPrimaryKey.handle(updateCustomerCustomerDemoInput.getCustomerId(), updateCustomerCustomerDemoInput.getCustomerTypeId());
		
    	customerCustomerDemo =
			customerCustomerDemoBuilder.build(findCustomersCustomerIdByPrimaryKey.handle(updateCustomerCustomerDemoInput.getCustomerId()),
					findCustomerDemographicsCustomerTypeIdByPrimaryKey.handle(updateCustomerCustomerDemoInput.getCustomerTypeId()));
		customerCustomerDemoRepository.update(customerCustomerDemo);
    
		return customerCustomerDemo;
	}
}
