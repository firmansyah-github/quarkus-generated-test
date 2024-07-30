// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.orderDetails.OrderDetails;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("orderDetails")
@RegisterForReflection
public class OrderDetailsResponse {

	@NotNull(message = ValidationMessages.ORDERDETAILS_UNITPRICE_MUST_BE_NOT_BLANK)
	@Max(8)
	private Double unitPrice;
	@NotNull(message = ValidationMessages.ORDERDETAILS_QUANTITY_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer quantity;
	@NotNull(message = ValidationMessages.ORDERDETAILS_DISCOUNT_MUST_BE_NOT_BLANK)
	@Max(8)
	private Double discount;
	private OrdersResponse ordersOrderIdResponse;
	private ProductsResponse productsProductIdResponse;
	

	public OrderDetailsResponse(OrderDetails orderDetails,  OrdersResponse ordersOrderIdResponse,  ProductsResponse productsProductIdResponse) {
								
		this.unitPrice = orderDetails.getUnitPrice();
		this.quantity = orderDetails.getQuantity();
		this.discount = orderDetails.getDiscount();
		this.ordersOrderIdResponse =ordersOrderIdResponse;
		this.productsProductIdResponse =productsProductIdResponse;
		

	}
    
	public OrderDetailsResponse(boolean isFlag, OrderDetails orderDetails) {
		if(isFlag){
			this.unitPrice = orderDetails.getUnitPrice();
			this.quantity = orderDetails.getQuantity();
			this.discount = orderDetails.getDiscount();
			
		}
	}
  
}
