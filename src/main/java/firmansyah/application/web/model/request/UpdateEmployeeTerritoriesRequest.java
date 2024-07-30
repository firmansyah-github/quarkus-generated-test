// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.employeeTerritories.UpdateEmployeeTerritoriesInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("employeeTerritories")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateEmployeeTerritoriesRequest {

	@NotNull(message = ValidationMessages.EMPLOYEETERRITORIES_EMPLOYEEID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer employeeId;
	@NotBlank(message = ValidationMessages.EMPLOYEETERRITORIES_TERRITORYID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.EMPLOYEETERRITORIES_TERRITORYID_MAX_LENGTH, max = 20)
	private String territoryId;

	public UpdateEmployeeTerritoriesInput toUpdateEmployeeTerritoriesInput() {
		return new UpdateEmployeeTerritoriesInput(
    		this.employeeId, this.territoryId
		);
  }
}
