// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCustomerDemographicsByFilter;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindCustomerDemographicsByFilterImpl implements FindCustomerDemographicsByFilter {

	private final CustomerDemographicsRepository customerDemographicsRepository;

	@Override
	public PageResult<CustomerDemographics> handle(ResourceFilter resourceFilterr) {
		return customerDemographicsRepository.findCustomerDemographicsByFilter(resourceFilterr);
	}
}
