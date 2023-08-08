package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;

import java.util.UUID;
import org.example.realworldapi.domain.exception.EmailAlreadyExistsException;
import org.example.realworldapi.domain.exception.UsernameAlreadyExistsException;
import org.example.realworldapi.domain.feature.CreateUser;
import org.example.realworldapi.domain.model.provider.HashProvider;
import org.example.realworldapi.domain.model.user.NewUserInput;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.model.user.UserModelBuilder;
import org.example.realworldapi.domain.model.user.UserRepository;


@AllArgsConstructor
public class CreateUserImpl implements CreateUser {

  	private final UserRepository userRepository;
	private final HashProvider hashProvider;
	private final UserModelBuilder userBuilder;

	@Override
	public User handle(NewUserInput newUserInput) {
		final var user = userBuilder.build(UUID.randomUUID(), newUserInput.getUsername(),
				newUserInput.getBio(), newUserInput.getImage(), newUserInput.getPassword(), newUserInput.getEmail());
		checkExistingUsername(user.getUsername());
		checkExistingEmail(user.getEmail());
		user.setPassword(hashProvider.hashPassword(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	private void checkExistingUsername(String username) {
		if (userRepository.existsBy("username", username)) {
			throw new UsernameAlreadyExistsException();
		}
	}

	private void checkExistingEmail(String email) {
		if (userRepository.existsBy("email", email)) {
			throw new EmailAlreadyExistsException();
		}
	}
}
