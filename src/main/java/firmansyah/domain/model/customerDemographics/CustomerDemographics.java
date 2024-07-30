// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.customerDemographics;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerDemographics {
	@NotBlank(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERTYPEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERTYPEID_MAX_LENGTH, max = 5)
	private String customerTypeId;
	@Size(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERDESC_MAX_LENGTH, max = 2147483647)
	private String customerDesc;
	private List<CustomerCustomerDemo> customerCustomerDemoCustomerTypeIdList;
	
	
}
