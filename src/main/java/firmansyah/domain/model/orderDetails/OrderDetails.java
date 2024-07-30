// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orderDetails;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;



import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.products.Products;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetails {
	@NotNull(message = ValidationMessages.ORDERDETAILS_UNITPRICE_MUST_BE_NOT_BLANK)
	@Max(8)
	private Double unitPrice;
	@NotNull(message = ValidationMessages.ORDERDETAILS_QUANTITY_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer quantity;
	@NotNull(message = ValidationMessages.ORDERDETAILS_DISCOUNT_MUST_BE_NOT_BLANK)
	@Max(8)
	private Double discount;
	
	private Orders ordersOrderId;
	private Products productsProductId;
	
}
