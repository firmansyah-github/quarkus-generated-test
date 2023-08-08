package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindUsersByFilter;
import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.users.UsersRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindUsersByFilterImpl implements FindUsersByFilter {

	private final UsersRepository usersRepository;

	@Override
	public PageResult<Users> handle(ResourceFilter resourceFilterr) {
		return usersRepository.findUsersByFilter(resourceFilterr);
	}
}
