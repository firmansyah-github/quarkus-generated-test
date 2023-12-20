// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.users.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateUsersImpl implements UpdateUsers {

	private final UsersRepository usersRepository;
	private final UsersModelBuilder usersBuilder;
	private final FindUsersByPrimaryKey findUsersByPrimaryKey;
	

	@Override
	public Users handle(UpdateUsersInput updateUsersInput) {
		var users = findUsersByPrimaryKey.handle(updateUsersInput.getId());
		
    	users =
			usersBuilder.build(updateUsersInput.getBio(),
					updateUsersInput.getEmail(),
					updateUsersInput.getId(),
					updateUsersInput.getImage(),
					updateUsersInput.getPassword(),
					updateUsersInput.getUsername(),
					null,
					null,
					null,
					null,
					null);
		usersRepository.update(users);
    
		return users;
	}
}
