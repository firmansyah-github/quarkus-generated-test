// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customers;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface CustomersRepository {

	void save(Customers customers);

	Optional<Customers> findCustomersByPrimaryKey(String customerId);

	void update(Customers customers);

	boolean delete(String customerId);

    PageResult<Customers> findCustomersByFilter(ResourceFilter resourceFilter);
    
	long countCustomers();
}
