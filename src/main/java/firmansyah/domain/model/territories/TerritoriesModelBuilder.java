// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.territories;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.region.Region;
import java.util.UUID;

@AllArgsConstructor
public class TerritoriesModelBuilder {

	private final ModelValidator modelValidator;

	public Territories build(String territoryId, String territoryDescription, List<EmployeeTerritories> employeeTerritoriesTerritoryIdList, Region regionRegionId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Territories(territoryId, territoryDescription, employeeTerritoriesTerritoryIdList, regionRegionId));
	}
  
}
