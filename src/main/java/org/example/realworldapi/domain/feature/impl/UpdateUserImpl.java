// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindUserById;
import org.example.realworldapi.domain.feature.UpdateUser;
import org.example.realworldapi.domain.exception.EmailAlreadyExistsException;
import org.example.realworldapi.domain.exception.UsernameAlreadyExistsException;
import org.example.realworldapi.domain.model.provider.HashProvider;
import org.example.realworldapi.domain.model.user.UpdateUserInput;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.model.user.UserRepository;
import org.example.realworldapi.domain.validator.ModelValidator;



@AllArgsConstructor
public class UpdateUserImpl implements UpdateUser {

  private final FindUserById findUserById;
  private final UserRepository userRepository;
  private final ModelValidator modelValidator;
  private final HashProvider hashProvider;

  @Override
  public User handle(UpdateUserInput updateUserInput) {
    final var user = findUserById.handle(updateUserInput.getId());
    checkValidations(updateUserInput, updateUserInput.getId());
    updateFields(user, updateUserInput);
    userRepository.update(modelValidator.validate(user));
    return user;
  }

  private void updateFields(User user, UpdateUserInput updateUserInput) {
    if (isPresent(updateUserInput.getUsername())) {
      user.setUsername(updateUserInput.getUsername());
    }

    if (isPresent(updateUserInput.getEmail())) {
      user.setEmail(updateUserInput.getEmail());
    }

    if (isPresent(updateUserInput.getBio())) {
      user.setBio(updateUserInput.getBio());
    }

    if (isPresent(updateUserInput.getImage())) {
      user.setImage(updateUserInput.getImage());
    }
    
    if (isPresent(updateUserInput.getPassword())) {
      user.setPassword(hashProvider.hashPassword(updateUserInput.getPassword()));
    }
  }

  private void checkValidations(UpdateUserInput updateUserInput, String excludeId) {

    if (isPresent(updateUserInput.getUsername())) {
      checkUsername(excludeId, updateUserInput.getUsername());
    }

    if (isPresent(updateUserInput.getEmail())) {
      checkEmail(excludeId, updateUserInput.getEmail());
    }
  }

  private boolean isPresent(String property) {
    return property != null && !property.isEmpty();
  }

  private void checkUsername(String selfId, String username) {
    if (userRepository.existsUsername(selfId, username)) {
      throw new UsernameAlreadyExistsException();
    }
  }

  private void checkEmail(String selfId, String email) {
    if (userRepository.existsEmail(selfId, email)) {
      throw new EmailAlreadyExistsException();
    }
  }
}
