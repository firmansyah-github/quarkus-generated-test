// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.region;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.territories.Territories;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class RegionModelBuilder {

	private final ModelValidator modelValidator;

	public Region build(Integer regionId, String regionDescription, List<Territories> territoriesRegionIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Region(regionId, regionDescription, territoriesRegionIdList));
	}
  
}
