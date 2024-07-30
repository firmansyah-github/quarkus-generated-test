// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindTerritoriesByPrimaryKey;
import firmansyah.domain.exception.TerritoriesNotFoundException;
import firmansyah.domain.model.territories.Territories;
import firmansyah.domain.model.territories.TerritoriesRepository;


@AllArgsConstructor
public class FindTerritoriesByPrimaryKeyImpl implements FindTerritoriesByPrimaryKey {

	private final TerritoriesRepository territoriesRepository;

	@Override
	public Territories handle(String territoryId) {
		return territoriesRepository.findTerritoriesByPrimaryKey(territoryId)
    			.orElseThrow(TerritoriesNotFoundException::new);
	}
}
