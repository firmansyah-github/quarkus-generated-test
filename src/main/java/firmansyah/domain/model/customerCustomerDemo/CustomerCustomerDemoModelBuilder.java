// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerCustomerDemo;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.customers.Customers;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import java.util.UUID;

@AllArgsConstructor
public class CustomerCustomerDemoModelBuilder {

	private final ModelValidator modelValidator;

	public CustomerCustomerDemo build(Customers customersCustomerId, CustomerDemographics customerDemographicsCustomerTypeId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new CustomerCustomerDemo(customersCustomerId, customerDemographicsCustomerTypeId));
	}
  
}
