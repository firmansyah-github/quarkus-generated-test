// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteUsers;
import firmansyah.domain.model.users.UsersRepository;


@AllArgsConstructor
public class DeleteUsersImpl implements DeleteUsers {

	private final UsersRepository usersRepository;

  	@Override
	public boolean handle(String id) {
		return usersRepository.delete(id);
	}
}
