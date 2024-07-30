// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.territories;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.region.Region;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Territories {
	@NotBlank(message = ValidationMessages.TERRITORIES_TERRITORYID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TERRITORIES_TERRITORYID_MAX_LENGTH, max = 20)
	private String territoryId;
	@NotBlank(message = ValidationMessages.TERRITORIES_TERRITORYDESCRIPTION_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TERRITORIES_TERRITORYDESCRIPTION_MAX_LENGTH, max = 60)
	private String territoryDescription;
	private List<EmployeeTerritories> employeeTerritoriesTerritoryIdList;
	
	private Region regionRegionId;
	
}
