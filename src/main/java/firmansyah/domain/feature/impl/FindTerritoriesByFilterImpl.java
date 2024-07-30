// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindTerritoriesByFilter;
import firmansyah.domain.model.territories.Territories;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.territories.TerritoriesRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindTerritoriesByFilterImpl implements FindTerritoriesByFilter {

	private final TerritoriesRepository territoriesRepository;

	@Override
	public PageResult<Territories> handle(ResourceFilter resourceFilterr) {
		return territoriesRepository.findTerritoriesByFilter(resourceFilterr);
	}
}
