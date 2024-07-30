// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.territories.Territories;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("territories")
@RegisterForReflection
public class TerritoriesResponse {

	@NotBlank(message = ValidationMessages.TERRITORIES_TERRITORYID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TERRITORIES_TERRITORYID_MAX_LENGTH, max = 20)
	private String territoryId;
	@NotBlank(message = ValidationMessages.TERRITORIES_TERRITORYDESCRIPTION_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TERRITORIES_TERRITORYDESCRIPTION_MAX_LENGTH, max = 60)
	private String territoryDescription;
	private RegionResponse regionRegionIdResponse;
	

	public TerritoriesResponse(Territories territories,  RegionResponse regionRegionIdResponse) {
								
		this.territoryId = territories.getTerritoryId();
		this.territoryDescription = territories.getTerritoryDescription();
		this.regionRegionIdResponse =regionRegionIdResponse;
		

	}
    
	public TerritoriesResponse(boolean isFlag, Territories territories) {
		if(isFlag){
			this.territoryId = territories.getTerritoryId();
			this.territoryDescription = territories.getTerritoryDescription();
			
		}
	}
  
}
