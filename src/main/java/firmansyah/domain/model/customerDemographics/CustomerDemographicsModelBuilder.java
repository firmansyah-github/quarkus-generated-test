// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerDemographics;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class CustomerDemographicsModelBuilder {

	private final ModelValidator modelValidator;

	public CustomerDemographics build(String customerTypeId, String customerDesc, List<CustomerCustomerDemo> customerCustomerDemoCustomerTypeIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new CustomerDemographics(customerTypeId, customerDesc, customerCustomerDemoCustomerTypeIdList));
	}
  
}
