// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateCustomerDemographics;
import firmansyah.domain.model.customerDemographics.*;
import firmansyah.domain.exception.CustomerDemographicsAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateCustomerDemographicsImpl implements CreateCustomerDemographics {

	private final CustomerDemographicsRepository customerDemographicsRepository;
	private final CustomerDemographicsModelBuilder customerDemographicsBuilder;
	

	@Override
	public CustomerDemographics handle(NewCustomerDemographicsInput newCustomerDemographicsInput) {
		final var customerDemographics =
			customerDemographicsBuilder.build(newCustomerDemographicsInput.getCustomerTypeId(),
					newCustomerDemographicsInput.getCustomerDesc(),
					null);
		
		if(customerDemographicsRepository.findCustomerDemographicsByPrimaryKey(customerDemographics.getCustomerTypeId()).isPresent()) {
			throw new CustomerDemographicsAlreadyExistsException();
		} else {
			customerDemographicsRepository.save(customerDemographics);
		}
   
		return customerDemographics;
	}
}
