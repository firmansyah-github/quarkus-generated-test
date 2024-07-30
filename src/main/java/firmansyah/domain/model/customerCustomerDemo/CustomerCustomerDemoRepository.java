// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerCustomerDemo;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface CustomerCustomerDemoRepository {

	void save(CustomerCustomerDemo customerCustomerDemo);

	Optional<CustomerCustomerDemo> findCustomerCustomerDemoByPrimaryKey(String customerId,String customerTypeId);

	void update(CustomerCustomerDemo customerCustomerDemo);

	boolean delete(String customerId,String customerTypeId);

    PageResult<CustomerCustomerDemo> findCustomerCustomerDemoByFilter(ResourceFilter resourceFilter);
    
	long countCustomerCustomerDemo();
}
