package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteUsers;
import org.example.realworldapi.domain.model.users.UsersRepository;


@AllArgsConstructor
public class DeleteUsersImpl implements DeleteUsers {

	private final UsersRepository usersRepository;

  	@Override
	public boolean handle(String id) {
		return usersRepository.delete(id);
	}
}
