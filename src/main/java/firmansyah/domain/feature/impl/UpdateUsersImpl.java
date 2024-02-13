// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
