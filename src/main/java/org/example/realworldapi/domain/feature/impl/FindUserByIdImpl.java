// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindUserById;
import org.example.realworldapi.domain.exception.UserNotFoundException;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.model.user.UserRepository;



@AllArgsConstructor
public class FindUserByIdImpl implements FindUserById {

  private final UserRepository userRepository;

  @Override
  public User handle(String id) {
    return userRepository.findUserById(id).orElseThrow(UserNotFoundException::new);
  }
}
