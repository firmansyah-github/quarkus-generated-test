// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import java.util.List;



public interface FindCustomerCustomerDemoByForeignKey {
  
	List<CustomerCustomerDemo> handleForCustomerId(java.lang.String CustomerId);
	List<CustomerCustomerDemo> handleForCustomerTypeId(java.lang.String CustomerTypeId);
}

