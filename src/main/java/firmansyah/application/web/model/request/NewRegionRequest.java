// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.region.NewRegionInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("region")
@RegisterForReflection
public class NewRegionRequest {
  
	@NotNull(message = ValidationMessages.REGION_REGIONID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer regionId;
	@NotBlank(message = ValidationMessages.REGION_REGIONDESCRIPTION_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.REGION_REGIONDESCRIPTION_MAX_LENGTH, max = 60)
	private String regionDescription;
  

	public NewRegionInput toNewRegionInput() {
		return new NewRegionInput(
			this.regionId, this.regionDescription
		);
  	}

}
