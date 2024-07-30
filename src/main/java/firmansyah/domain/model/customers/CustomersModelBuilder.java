// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customers;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class CustomersModelBuilder {

	private final ModelValidator modelValidator;

	public Customers build(String customerId, String companyName, String contactName, String contactTitle, String address, String city, String region, String postalCode, String country, String phone, String fax, List<Orders> ordersCustomerIdList, List<CustomerCustomerDemo> customerCustomerDemoCustomerIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Customers(customerId, companyName, contactName, contactTitle, address, city, region, postalCode, country, phone, fax, ordersCustomerIdList, customerCustomerDemoCustomerIdList));
	}
  
}
