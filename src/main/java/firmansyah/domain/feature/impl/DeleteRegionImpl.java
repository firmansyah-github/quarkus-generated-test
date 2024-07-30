// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteRegion;
import firmansyah.domain.model.region.RegionRepository;


@AllArgsConstructor
public class DeleteRegionImpl implements DeleteRegion {

	private final RegionRepository regionRepository;

  	@Override
	public boolean handle(Integer regionId) {
		return regionRepository.delete(regionId);
	}
}
