// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindUsersByFilter {
	PageResult<Users> handle(ResourceFilter resourceFilter);
}