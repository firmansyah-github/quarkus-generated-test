// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateOrderDetailsInput {
	private Integer orderId;
	private Integer productId;
	private Double unitPrice;
	private Integer quantity;
	private Double discount;
}
