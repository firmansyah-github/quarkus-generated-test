// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.orders.Orders;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("orders")
@RegisterForReflection
public class OrdersResponse {

	@NotNull(message = ValidationMessages.ORDERS_ORDERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer orderId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime orderDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime requiredDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime shippedDate;
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
	private EmployeesResponse employeesEmployeeIdResponse;
	private ShippersResponse shippersShipViaResponse;
	private CustomersResponse customersCustomerIdResponse;
	

	public OrdersResponse(Orders orders,  EmployeesResponse employeesEmployeeIdResponse,  ShippersResponse shippersShipViaResponse,  CustomersResponse customersCustomerIdResponse) {
								
		this.orderId = orders.getOrderId();
		this.orderDate = orders.getOrderDate();
		this.requiredDate = orders.getRequiredDate();
		this.shippedDate = orders.getShippedDate();
		this.freight = orders.getFreight();
		this.shipName = orders.getShipName();
		this.shipAddress = orders.getShipAddress();
		this.shipCity = orders.getShipCity();
		this.shipRegion = orders.getShipRegion();
		this.shipPostalCode = orders.getShipPostalCode();
		this.shipCountry = orders.getShipCountry();
		this.employeesEmployeeIdResponse =employeesEmployeeIdResponse;
		this.shippersShipViaResponse =shippersShipViaResponse;
		this.customersCustomerIdResponse =customersCustomerIdResponse;
		

	}
    
	public OrdersResponse(boolean isFlag, Orders orders) {
		if(isFlag){
			this.orderId = orders.getOrderId();
			this.orderDate = orders.getOrderDate();
			this.requiredDate = orders.getRequiredDate();
			this.shippedDate = orders.getShippedDate();
			this.freight = orders.getFreight();
			this.shipName = orders.getShipName();
			this.shipAddress = orders.getShipAddress();
			this.shipCity = orders.getShipCity();
			this.shipRegion = orders.getShipRegion();
			this.shipPostalCode = orders.getShipPostalCode();
			this.shipCountry = orders.getShipCountry();
			
		}
	}
  
}
