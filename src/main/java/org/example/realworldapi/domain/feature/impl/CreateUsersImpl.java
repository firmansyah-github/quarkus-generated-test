package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateUsers;
import org.example.realworldapi.domain.model.users.*;
import org.example.realworldapi.domain.exception.UsersAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateUsersImpl implements CreateUsers {

	private final UsersRepository usersRepository;
	private final UsersModelBuilder usersBuilder;
	

	@Override
	public Users handle(NewUsersInput newUsersInput) {
		final var users =
			usersBuilder.build(newUsersInput.getId(),
					newUsersInput.getBio(),
					newUsersInput.getEmail(),
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
