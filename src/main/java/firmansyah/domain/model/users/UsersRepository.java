// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
