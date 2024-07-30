// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.territories.UpdateTerritoriesInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("territories")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateTerritoriesRequest {

	@NotBlank(message = ValidationMessages.TERRITORIES_TERRITORYID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TERRITORIES_TERRITORYID_MAX_LENGTH, max = 20)
	private String territoryId;
	@NotBlank(message = ValidationMessages.TERRITORIES_TERRITORYDESCRIPTION_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TERRITORIES_TERRITORYDESCRIPTION_MAX_LENGTH, max = 60)
	private String territoryDescription;
	@NotNull(message = ValidationMessages.TERRITORIES_REGIONID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer regionId;

	public UpdateTerritoriesInput toUpdateTerritoriesInput() {
		return new UpdateTerritoriesInput(
    		this.territoryId, this.territoryDescription, this.regionId
		);
  }
}
