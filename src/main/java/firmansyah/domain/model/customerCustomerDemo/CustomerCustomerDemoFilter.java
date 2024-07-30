// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerCustomerDemo;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class CustomerCustomerDemoFilter {
	private final int offset;
	private final int limit;
	private String customerId;
	private String customerTypeId;
}
