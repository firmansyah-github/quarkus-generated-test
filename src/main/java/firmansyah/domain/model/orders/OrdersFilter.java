// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orders;

import lombok.AllArgsConstructor;
import lombok.Data;

            

import java.time.LocalDateTime;

import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class OrdersFilter {
	private final int offset;
	private final int limit;
	private Integer orderId;
	private String customerId;
	private Integer employeeId;
	private LocalDateTime orderDate;
	private LocalDateTime requiredDate;
	private LocalDateTime shippedDate;
	private Integer shipVia;
	private Double freight;
	private String shipName;
	private String shipAddress;
	private String shipCity;
	private String shipRegion;
	private String shipPostalCode;
	private String shipCountry;
}
