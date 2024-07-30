// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customers;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class CustomersFilter {
	private final int offset;
	private final int limit;
	private String customerId;
	private String companyName;
	private String contactName;
	private String contactTitle;
	private String address;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	private String phone;
	private String fax;
}
