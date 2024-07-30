// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.usStates.NewUsStatesInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("usStates")
@RegisterForReflection
public class NewUsStatesRequest {
  
	@NotNull(message = ValidationMessages.USSTATES_STATEID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer stateId;
	@Size(message = ValidationMessages.USSTATES_STATENAME_MAX_LENGTH, max = 100)
	private String stateName;
	@Size(message = ValidationMessages.USSTATES_STATEABBR_MAX_LENGTH, max = 2)
	private String stateAbbr;
	@Size(message = ValidationMessages.USSTATES_STATEREGION_MAX_LENGTH, max = 50)
	private String stateRegion;
  

	public NewUsStatesInput toNewUsStatesInput() {
		return new NewUsStatesInput(
			this.stateId, this.stateName, this.stateAbbr, this.stateRegion
		);
  	}

}
