// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("customerCustomerDemo")
@RegisterForReflection
public class CustomerCustomerDemoResponse {

	private CustomersResponse customersCustomerIdResponse;
	private CustomerDemographicsResponse customerDemographicsCustomerTypeIdResponse;
	

	public CustomerCustomerDemoResponse(CustomerCustomerDemo customerCustomerDemo,  CustomersResponse customersCustomerIdResponse,  CustomerDemographicsResponse customerDemographicsCustomerTypeIdResponse) {
								
		this.customersCustomerIdResponse =customersCustomerIdResponse;
		this.customerDemographicsCustomerTypeIdResponse =customerDemographicsCustomerTypeIdResponse;
		

	}
    
  
}
