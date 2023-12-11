// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindUsersByFilter {
	PageResult<Users> handle(ResourceFilter resourceFilter);
}