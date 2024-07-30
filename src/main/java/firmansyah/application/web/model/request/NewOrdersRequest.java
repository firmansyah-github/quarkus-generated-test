// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.orders.NewOrdersInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;



@Getter
@Setter
@JsonRootName("orders")
@RegisterForReflection
public class NewOrdersRequest {
  
	@NotNull(message = ValidationMessages.ORDERS_ORDERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer orderId;
	@Size(message = ValidationMessages.ORDERS_CUSTOMERID_MAX_LENGTH, max = 5)
	private String customerId;
	@Max(5)
	private Integer employeeId;
	private LocalDateTime orderDate;
	private LocalDateTime requiredDate;
	private LocalDateTime shippedDate;
	@Max(5)
	private Integer shipVia;
	@Max(8)
	private Double freight;
	@Size(message = ValidationMessages.ORDERS_SHIPNAME_MAX_LENGTH, max = 40)
	private String shipName;
	@Size(message = ValidationMessages.ORDERS_SHIPADDRESS_MAX_LENGTH, max = 60)
	private String shipAddress;
	@Size(message = ValidationMessages.ORDERS_SHIPCITY_MAX_LENGTH, max = 15)
	private String shipCity;
	@Size(message = ValidationMessages.ORDERS_SHIPREGION_MAX_LENGTH, max = 15)
	private String shipRegion;
	@Size(message = ValidationMessages.ORDERS_SHIPPOSTALCODE_MAX_LENGTH, max = 10)
	private String shipPostalCode;
	@Size(message = ValidationMessages.ORDERS_SHIPCOUNTRY_MAX_LENGTH, max = 15)
	private String shipCountry;
  

	public NewOrdersInput toNewOrdersInput() {
		return new NewOrdersInput(
			this.orderId, this.customerId, this.employeeId, this.orderDate, this.requiredDate, this.shippedDate, this.shipVia, this.freight, this.shipName, this.shipAddress, this.shipCity, this.shipRegion, this.shipPostalCode, this.shipCountry
		);
  	}

}
