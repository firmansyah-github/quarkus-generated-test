// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteCustomerDemographics;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsRepository;


@AllArgsConstructor
public class DeleteCustomerDemographicsImpl implements DeleteCustomerDemographics {

	private final CustomerDemographicsRepository customerDemographicsRepository;

  	@Override
	public boolean handle(String customerTypeId) {
		return customerDemographicsRepository.delete(customerTypeId);
	}
}
