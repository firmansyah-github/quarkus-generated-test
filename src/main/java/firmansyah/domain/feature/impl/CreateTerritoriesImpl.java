// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateTerritories;
import firmansyah.domain.model.territories.*;
import firmansyah.domain.exception.TerritoriesAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateTerritoriesImpl implements CreateTerritories {

	private final TerritoriesRepository territoriesRepository;
	private final TerritoriesModelBuilder territoriesBuilder;
	private final FindRegionByPrimaryKey findRegionRegionIdByPrimaryKey;
	

	@Override
	public Territories handle(NewTerritoriesInput newTerritoriesInput) {
		final var territories =
			territoriesBuilder.build(newTerritoriesInput.getTerritoryId(),
					newTerritoriesInput.getTerritoryDescription(),
					null,
					findRegionRegionIdByPrimaryKey.handle(newTerritoriesInput.getRegionId()));
		
		if(territoriesRepository.findTerritoriesByPrimaryKey(territories.getTerritoryId()).isPresent()) {
			throw new TerritoriesAlreadyExistsException();
		} else {
			territoriesRepository.save(territories);
		}
   
		return territories;
	}
}
