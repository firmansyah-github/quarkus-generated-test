// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.region.Region;
import firmansyah.domain.model.region.NewRegionInput;

public interface CreateRegion {
	Region handle(NewRegionInput newRegionInput);
}
