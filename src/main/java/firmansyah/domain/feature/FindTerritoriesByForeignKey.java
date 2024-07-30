// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.territories.Territories;
import java.util.List;



public interface FindTerritoriesByForeignKey {
  
	List<Territories> handleForRegionId(java.lang.Integer RegionId);
}

