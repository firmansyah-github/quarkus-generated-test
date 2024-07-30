// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.territories.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateTerritoriesImpl implements UpdateTerritories {

	private final TerritoriesRepository territoriesRepository;
	private final TerritoriesModelBuilder territoriesBuilder;
	private final FindTerritoriesByPrimaryKey findTerritoriesByPrimaryKey;
	private final FindRegionByPrimaryKey findRegionRegionIdByPrimaryKey;
	

	@Override
	public Territories handle(UpdateTerritoriesInput updateTerritoriesInput) {
		var territories = findTerritoriesByPrimaryKey.handle(updateTerritoriesInput.getTerritoryId());
		
    	territories =
			territoriesBuilder.build(updateTerritoriesInput.getTerritoryId(),
					updateTerritoriesInput.getTerritoryDescription(),
					null,
					findRegionRegionIdByPrimaryKey.handle(updateTerritoriesInput.getRegionId()));
		territoriesRepository.update(territories);
    
		return territories;
	}
}
