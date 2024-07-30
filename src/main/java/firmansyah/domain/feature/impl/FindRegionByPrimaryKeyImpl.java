// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindRegionByPrimaryKey;
import firmansyah.domain.exception.RegionNotFoundException;
import firmansyah.domain.model.region.Region;
import firmansyah.domain.model.region.RegionRepository;


@AllArgsConstructor
public class FindRegionByPrimaryKeyImpl implements FindRegionByPrimaryKey {

	private final RegionRepository regionRepository;

	@Override
	public Region handle(Integer regionId) {
		return regionRepository.findRegionByPrimaryKey(regionId)
    			.orElseThrow(RegionNotFoundException::new);
	}
}
