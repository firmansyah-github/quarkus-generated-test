// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateRegion;
import firmansyah.domain.model.region.*;
import firmansyah.domain.exception.RegionAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateRegionImpl implements CreateRegion {

	private final RegionRepository regionRepository;
	private final RegionModelBuilder regionBuilder;
	

	@Override
	public Region handle(NewRegionInput newRegionInput) {
		final var region =
			regionBuilder.build(newRegionInput.getRegionId(),
					newRegionInput.getRegionDescription(),
					null);
		
		if(regionRepository.findRegionByPrimaryKey(region.getRegionId()).isPresent()) {
			throw new RegionAlreadyExistsException();
		} else {
			regionRepository.save(region);
		}
   
		return region;
	}
}
