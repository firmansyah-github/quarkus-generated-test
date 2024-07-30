// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerDemographics;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface CustomerDemographicsRepository {

	void save(CustomerDemographics customerDemographics);

	Optional<CustomerDemographics> findCustomerDemographicsByPrimaryKey(String customerTypeId);

	void update(CustomerDemographics customerDemographics);

	boolean delete(String customerTypeId);

    PageResult<CustomerDemographics> findCustomerDemographicsByFilter(ResourceFilter resourceFilter);
    
	long countCustomerDemographics();
}
