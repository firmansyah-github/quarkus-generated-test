// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.region.UpdateRegionInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("region")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateRegionRequest {

	@NotNull(message = ValidationMessages.REGION_REGIONID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer regionId;
	@NotBlank(message = ValidationMessages.REGION_REGIONDESCRIPTION_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.REGION_REGIONDESCRIPTION_MAX_LENGTH, max = 60)
	private String regionDescription;

	public UpdateRegionInput toUpdateRegionInput() {
		return new UpdateRegionInput(
    		this.regionId, this.regionDescription
		);
  }
}
