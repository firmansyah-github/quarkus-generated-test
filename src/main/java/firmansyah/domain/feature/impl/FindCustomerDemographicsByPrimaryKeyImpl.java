// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCustomerDemographicsByPrimaryKey;
import firmansyah.domain.exception.CustomerDemographicsNotFoundException;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsRepository;


@AllArgsConstructor
public class FindCustomerDemographicsByPrimaryKeyImpl implements FindCustomerDemographicsByPrimaryKey {

	private final CustomerDemographicsRepository customerDemographicsRepository;

	@Override
	public CustomerDemographics handle(String customerTypeId) {
		return customerDemographicsRepository.findCustomerDemographicsByPrimaryKey(customerTypeId)
    			.orElseThrow(CustomerDemographicsNotFoundException::new);
	}
}
