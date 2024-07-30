// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerCustomerDemo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;



import firmansyah.domain.model.customers.Customers;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerCustomerDemo {
	
	private Customers customersCustomerId;
	private CustomerDemographics customerDemographicsCustomerTypeId;
	
}
