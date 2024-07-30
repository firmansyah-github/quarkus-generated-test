// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.shippers.UpdateShippersInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("shippers")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateShippersRequest {

	@NotNull(message = ValidationMessages.SHIPPERS_SHIPPERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer shipperId;
	@NotBlank(message = ValidationMessages.SHIPPERS_COMPANYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SHIPPERS_COMPANYNAME_MAX_LENGTH, max = 40)
	private String companyName;
	@Size(message = ValidationMessages.SHIPPERS_PHONE_MAX_LENGTH, max = 24)
	private String phone;

	public UpdateShippersInput toUpdateShippersInput() {
		return new UpdateShippersInput(
    		this.shipperId, this.companyName, this.phone
		);
  }
}
