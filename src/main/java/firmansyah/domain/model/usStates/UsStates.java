// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.usStates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UsStates {
	@NotNull(message = ValidationMessages.USSTATES_STATEID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer stateId;
	@Size(message = ValidationMessages.USSTATES_STATENAME_MAX_LENGTH, max = 100)
	private String stateName;
	@Size(message = ValidationMessages.USSTATES_STATEABBR_MAX_LENGTH, max = 2)
	private String stateAbbr;
	@Size(message = ValidationMessages.USSTATES_STATEREGION_MAX_LENGTH, max = 50)
	private String stateRegion;
	
	
}
