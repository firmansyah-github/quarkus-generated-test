// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.orderDetails.UpdateOrderDetailsInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("orderDetails")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateOrderDetailsRequest {

	@NotNull(message = ValidationMessages.ORDERDETAILS_ORDERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer orderId;
	@NotNull(message = ValidationMessages.ORDERDETAILS_PRODUCTID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer productId;
	@NotNull(message = ValidationMessages.ORDERDETAILS_UNITPRICE_MUST_BE_NOT_BLANK)
	@Max(8)
	private Double unitPrice;
	@NotNull(message = ValidationMessages.ORDERDETAILS_QUANTITY_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer quantity;
	@NotNull(message = ValidationMessages.ORDERDETAILS_DISCOUNT_MUST_BE_NOT_BLANK)
	@Max(8)
	private Double discount;

	public UpdateOrderDetailsInput toUpdateOrderDetailsInput() {
		return new UpdateOrderDetailsInput(
    		this.orderId, this.productId, this.unitPrice, this.quantity, this.discount
		);
  }
}
