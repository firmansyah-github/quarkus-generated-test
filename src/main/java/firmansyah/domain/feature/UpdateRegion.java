// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.region.Region;
import firmansyah.domain.model.region.UpdateRegionInput;


public interface UpdateRegion {
	Region handle(UpdateRegionInput updateRegionInput);
}
