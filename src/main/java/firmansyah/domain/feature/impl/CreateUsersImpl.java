// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateUsers;
import firmansyah.domain.model.users.*;
import firmansyah.domain.exception.UsersAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateUsersImpl implements CreateUsers {

	private final UsersRepository usersRepository;
	private final UsersModelBuilder usersBuilder;
	

	@Override
	public Users handle(NewUsersInput newUsersInput) {
		final var users =
			usersBuilder.build(newUsersInput.getBio(),
					newUsersInput.getEmail(),
					newUsersInput.getId(),
					newUsersInput.getImage(),
					newUsersInput.getPassword(),
					newUsersInput.getUsername(),
					null,
					null,
					null,
					null,
					null);
		
		if(usersRepository.findUsersByPrimaryKey(users.getId()).isPresent()) {
			throw new UsersAlreadyExistsException();
		} else {
			usersRepository.save(users);
		}
   
		return users;
	}
}
