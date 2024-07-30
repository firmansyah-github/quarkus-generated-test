// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerDemographics;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class CustomerDemographicsFilter {
	private final int offset;
	private final int limit;
	private String customerTypeId;
	private String customerDesc;
}
