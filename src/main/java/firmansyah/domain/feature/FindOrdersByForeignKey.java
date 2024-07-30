// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.orders.Orders;
import java.util.List;



public interface FindOrdersByForeignKey {
  
	List<Orders> handleForEmployeeId(java.lang.Integer EmployeeId);
	List<Orders> handleForShipVia(java.lang.Integer ShipVia);
	List<Orders> handleForCustomerId(java.lang.String CustomerId);
}

