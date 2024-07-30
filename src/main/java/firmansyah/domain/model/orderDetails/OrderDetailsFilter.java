// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class OrderDetailsFilter {
	private final int offset;
	private final int limit;
	private Integer orderId;
	private Integer productId;
	private Double unitPrice;
	private Integer quantity;
	private Double discount;
}
