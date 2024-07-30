// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.model.customers.Customers;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
	@NotNull(message = ValidationMessages.ORDERS_ORDERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer orderId;
	private LocalDateTime orderDate;
	private LocalDateTime requiredDate;
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
	private List<OrderDetails> orderDetailsOrderIdList;
	
	private Employees employeesEmployeeId;
	private Shippers shippersShipVia;
	private Customers customersCustomerId;
	
}
