// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("customerDemographics")
@RegisterForReflection
public class CustomerDemographicsResponse {

	@NotBlank(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERTYPEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERTYPEID_MAX_LENGTH, max = 5)
	private String customerTypeId;
	@Size(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERDESC_MAX_LENGTH, max = 2147483647)
	private String customerDesc;
	

	public CustomerDemographicsResponse(CustomerDemographics customerDemographics) {
								
		this.customerTypeId = customerDemographics.getCustomerTypeId();
		this.customerDesc = customerDemographics.getCustomerDesc();
		

	}
    
	public CustomerDemographicsResponse(boolean isFlag, CustomerDemographics customerDemographics) {
		if(isFlag){
			this.customerTypeId = customerDemographics.getCustomerTypeId();
			this.customerDesc = customerDemographics.getCustomerDesc();
			
		}
	}
  
}
