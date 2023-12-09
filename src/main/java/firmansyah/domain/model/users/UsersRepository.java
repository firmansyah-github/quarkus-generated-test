// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.users;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface UsersRepository {

	void save(Users users);

	Optional<Users> findUsersByPrimaryKey(String id);

	void update(Users users);

	boolean delete(String id);

    PageResult<Users> findUsersByFilter(ResourceFilter resourceFilter);
    
	long countUsers();
}
