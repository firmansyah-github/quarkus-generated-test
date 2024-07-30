// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.territories;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface TerritoriesRepository {

	void save(Territories territories);

	Optional<Territories> findTerritoriesByPrimaryKey(String territoryId);

	void update(Territories territories);

	boolean delete(String territoryId);

    PageResult<Territories> findTerritoriesByFilter(ResourceFilter resourceFilter);
    
	long countTerritories();
}
