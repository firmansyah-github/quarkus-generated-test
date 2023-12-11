// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindUsersByPrimaryKey;
import firmansyah.domain.exception.UsersNotFoundException;
import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UsersRepository;


@AllArgsConstructor
public class FindUsersByPrimaryKeyImpl implements FindUsersByPrimaryKey {

	private final UsersRepository usersRepository;

	@Override
	public Users handle(String id) {
		return usersRepository.findUsersByPrimaryKey(id)
    			.orElseThrow(UsersNotFoundException::new);
	}
}
