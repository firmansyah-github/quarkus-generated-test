// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.region.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateRegionImpl implements UpdateRegion {

	private final RegionRepository regionRepository;
	private final RegionModelBuilder regionBuilder;
	private final FindRegionByPrimaryKey findRegionByPrimaryKey;
	

	@Override
	public Region handle(UpdateRegionInput updateRegionInput) {
		var region = findRegionByPrimaryKey.handle(updateRegionInput.getRegionId());
		
    	region =
			regionBuilder.build(updateRegionInput.getRegionId(),
					updateRegionInput.getRegionDescription(),
					null);
		regionRepository.update(region);
    
		return region;
	}
}
