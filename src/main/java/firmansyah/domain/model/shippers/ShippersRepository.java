// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.shippers;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface ShippersRepository {

	void save(Shippers shippers);

	Optional<Shippers> findShippersByPrimaryKey(Integer shipperId);

	void update(Shippers shippers);

	boolean delete(Integer shipperId);

    PageResult<Shippers> findShippersByFilter(ResourceFilter resourceFilter);
    
	long countShippers();
}
