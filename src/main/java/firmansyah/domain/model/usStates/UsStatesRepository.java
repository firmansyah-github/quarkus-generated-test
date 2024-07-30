// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.usStates;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface UsStatesRepository {

	void save(UsStates usStates);

	Optional<UsStates> findUsStatesByPrimaryKey(Integer stateId);

	void update(UsStates usStates);

	boolean delete(Integer stateId);

    PageResult<UsStates> findUsStatesByFilter(ResourceFilter resourceFilter);
    
	long countUsStates();
}
