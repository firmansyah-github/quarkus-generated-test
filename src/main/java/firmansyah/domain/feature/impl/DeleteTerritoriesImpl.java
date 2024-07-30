// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteTerritories;
import firmansyah.domain.model.territories.TerritoriesRepository;


@AllArgsConstructor
public class DeleteTerritoriesImpl implements DeleteTerritories {

	private final TerritoriesRepository territoriesRepository;

  	@Override
	public boolean handle(String territoryId) {
		return territoriesRepository.delete(territoryId);
	}
}
