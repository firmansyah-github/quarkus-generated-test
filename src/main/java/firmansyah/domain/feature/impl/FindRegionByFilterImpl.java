// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindRegionByFilter;
import firmansyah.domain.model.region.Region;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.region.RegionRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindRegionByFilterImpl implements FindRegionByFilter {

	private final RegionRepository regionRepository;

	@Override
	public PageResult<Region> handle(ResourceFilter resourceFilterr) {
		return regionRepository.findRegionByFilter(resourceFilterr);
	}
}
