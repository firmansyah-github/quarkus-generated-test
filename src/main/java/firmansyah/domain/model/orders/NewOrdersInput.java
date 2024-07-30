// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.orders;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class NewOrdersInput {
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
