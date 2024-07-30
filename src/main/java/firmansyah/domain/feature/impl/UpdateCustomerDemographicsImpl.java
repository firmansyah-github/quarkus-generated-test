// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.customerDemographics.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateCustomerDemographicsImpl implements UpdateCustomerDemographics {

	private final CustomerDemographicsRepository customerDemographicsRepository;
	private final CustomerDemographicsModelBuilder customerDemographicsBuilder;
	private final FindCustomerDemographicsByPrimaryKey findCustomerDemographicsByPrimaryKey;
	

	@Override
	public CustomerDemographics handle(UpdateCustomerDemographicsInput updateCustomerDemographicsInput) {
		var customerDemographics = findCustomerDemographicsByPrimaryKey.handle(updateCustomerDemographicsInput.getCustomerTypeId());
		
    	customerDemographics =
			customerDemographicsBuilder.build(updateCustomerDemographicsInput.getCustomerTypeId(),
					updateCustomerDemographicsInput.getCustomerDesc(),
					null);
		customerDemographicsRepository.update(customerDemographics);
    
		return customerDemographics;
	}
}
