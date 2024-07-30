// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateCustomerCustomerDemo;
import firmansyah.domain.model.customerCustomerDemo.*;
import firmansyah.domain.exception.CustomerCustomerDemoAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateCustomerCustomerDemoImpl implements CreateCustomerCustomerDemo {

	private final CustomerCustomerDemoRepository customerCustomerDemoRepository;
	private final CustomerCustomerDemoModelBuilder customerCustomerDemoBuilder;
	private final FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey;
	private final FindCustomerDemographicsByPrimaryKey findCustomerDemographicsCustomerTypeIdByPrimaryKey;
	

	@Override
	public CustomerCustomerDemo handle(NewCustomerCustomerDemoInput newCustomerCustomerDemoInput) {
		final var customerCustomerDemo =
			customerCustomerDemoBuilder.build(findCustomersCustomerIdByPrimaryKey.handle(newCustomerCustomerDemoInput.getCustomerId()),
					findCustomerDemographicsCustomerTypeIdByPrimaryKey.handle(newCustomerCustomerDemoInput.getCustomerTypeId()));
		
		if(customerCustomerDemoRepository.findCustomerCustomerDemoByPrimaryKey(customerCustomerDemo.getCustomersCustomerId().getCustomerId(), customerCustomerDemo.getCustomerDemographicsCustomerTypeId().getCustomerTypeId()).isPresent()) {
			throw new CustomerCustomerDemoAlreadyExistsException();
		} else {
			customerCustomerDemoRepository.save(customerCustomerDemo);
		}
   
		return customerCustomerDemo;
	}
}
