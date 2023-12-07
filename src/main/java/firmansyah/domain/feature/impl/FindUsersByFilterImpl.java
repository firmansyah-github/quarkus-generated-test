// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindUsersByFilter;
import firmansyah.domain.model.users.Users;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.users.UsersRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindUsersByFilterImpl implements FindUsersByFilter {

	private final UsersRepository usersRepository;

	@Override
	public PageResult<Users> handle(ResourceFilter resourceFilterr) {
		return usersRepository.findUsersByFilter(resourceFilterr);
	}
}
