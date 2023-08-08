package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindUsersByPrimaryKey;
import org.example.realworldapi.domain.exception.UsersNotFoundException;
import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.users.UsersRepository;


@AllArgsConstructor
public class FindUsersByPrimaryKeyImpl implements FindUsersByPrimaryKey {

	private final UsersRepository usersRepository;

	@Override
	public Users handle(String id) {
		return usersRepository.findUsersByPrimaryKey(id)
    			.orElseThrow(UsersNotFoundException::new);
	}
}
