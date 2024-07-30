// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.region;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.territories.Territories;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
	@NotNull(message = ValidationMessages.REGION_REGIONID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer regionId;
	@NotBlank(message = ValidationMessages.REGION_REGIONDESCRIPTION_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.REGION_REGIONDESCRIPTION_MAX_LENGTH, max = 60)
	private String regionDescription;
	private List<Territories> territoriesRegionIdList;
	
	
}
