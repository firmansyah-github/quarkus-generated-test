// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.customerCustomerDemo.UpdateCustomerCustomerDemoInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("customerCustomerDemo")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateCustomerCustomerDemoRequest {

	@NotBlank(message = ValidationMessages.CUSTOMERCUSTOMERDEMO_CUSTOMERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERCUSTOMERDEMO_CUSTOMERID_MAX_LENGTH, max = 5)
	private String customerId;
	@NotBlank(message = ValidationMessages.CUSTOMERCUSTOMERDEMO_CUSTOMERTYPEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERCUSTOMERDEMO_CUSTOMERTYPEID_MAX_LENGTH, max = 5)
	private String customerTypeId;

	public UpdateCustomerCustomerDemoInput toUpdateCustomerCustomerDemoInput() {
		return new UpdateCustomerCustomerDemoInput(
    		this.customerId, this.customerTypeId
		);
  }
}
