// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.customers.Customers;
import firmansyah.domain.model.customers.UpdateCustomersInput;


public interface UpdateCustomers {
	Customers handle(UpdateCustomersInput updateCustomersInput);
}
