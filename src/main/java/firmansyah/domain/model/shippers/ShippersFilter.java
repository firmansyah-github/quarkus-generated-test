// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.shippers;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.orders.Orders;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class ShippersFilter {
	private final int offset;
	private final int limit;
	private Integer shipperId;
	private String companyName;
	private String phone;
}
