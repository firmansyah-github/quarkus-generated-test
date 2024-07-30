// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.shippers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.orders.Orders;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Shippers {
	@NotNull(message = ValidationMessages.SHIPPERS_SHIPPERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer shipperId;
	@NotBlank(message = ValidationMessages.SHIPPERS_COMPANYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SHIPPERS_COMPANYNAME_MAX_LENGTH, max = 40)
	private String companyName;
	@Size(message = ValidationMessages.SHIPPERS_PHONE_MAX_LENGTH, max = 24)
	private String phone;
	private List<Orders> ordersShipViaList;
	
	
}
