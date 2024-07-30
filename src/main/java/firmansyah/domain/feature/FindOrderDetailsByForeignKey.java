// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;



public interface FindOrderDetailsByForeignKey {
  
	List<OrderDetails> handleForOrderId(java.lang.Integer OrderId);
	List<OrderDetails> handleForProductId(java.lang.Integer ProductId);
}

