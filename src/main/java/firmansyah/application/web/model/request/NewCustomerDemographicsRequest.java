// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.customerDemographics.NewCustomerDemographicsInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("customerDemographics")
@RegisterForReflection
public class NewCustomerDemographicsRequest {
  
	@NotBlank(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERTYPEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERTYPEID_MAX_LENGTH, max = 5)
	private String customerTypeId;
	@Size(message = ValidationMessages.CUSTOMERDEMOGRAPHICS_CUSTOMERDESC_MAX_LENGTH, max = 2147483647)
	private String customerDesc;
  

	public NewCustomerDemographicsInput toNewCustomerDemographicsInput() {
		return new NewCustomerDemographicsInput(
			this.customerTypeId, this.customerDesc
		);
  	}

}
