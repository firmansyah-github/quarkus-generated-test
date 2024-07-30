// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.region;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface RegionRepository {

	void save(Region region);

	Optional<Region> findRegionByPrimaryKey(Integer regionId);

	void update(Region region);

	boolean delete(Integer regionId);

    PageResult<Region> findRegionByFilter(ResourceFilter resourceFilter);
    
	long countRegion();
}
