package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.users.*;
import org.example.realworldapi.domain.feature.*;

@AllArgsConstructor
public class UpdateUsersImpl implements UpdateUsers {

	private final UsersRepository usersRepository;
	private final UsersModelBuilder usersBuilder;
	private final FindUsersByPrimaryKey findUsersByPrimaryKey;
	

	@Override
	public Users handle(UpdateUsersInput updateUsersInput) {
		var users = findUsersByPrimaryKey.handle(updateUsersInput.getId());
		
    	users =
			usersBuilder.build(updateUsersInput.getId(),
					updateUsersInput.getBio(),
					updateUsersInput.getEmail(),
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
