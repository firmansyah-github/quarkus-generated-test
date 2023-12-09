// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
