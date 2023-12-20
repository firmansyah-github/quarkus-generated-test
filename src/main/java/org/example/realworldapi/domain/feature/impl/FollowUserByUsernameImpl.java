// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindUserById;
import org.example.realworldapi.domain.feature.FindUserByUsername;
import org.example.realworldapi.domain.feature.FollowUserByUsername;
import org.example.realworldapi.domain.model.user.FollowRelationship;
import org.example.realworldapi.domain.model.user.FollowRelationshipRepository;



@AllArgsConstructor
public class FollowUserByUsernameImpl implements FollowUserByUsername {

  private final FindUserById findUserById;
  private final FindUserByUsername findUserByUsername;
  private final FollowRelationshipRepository usersFollowedRepository;

  @Override
  public FollowRelationship handle(String loggedUserId, String username) {
    final var loggedUser = findUserById.handle(loggedUserId);
    final var userToFollow = findUserByUsername.handle(username);
    final var followingRelationship = new FollowRelationship(loggedUser, userToFollow);
    usersFollowedRepository.save(followingRelationship);
    return followingRelationship;
  }
}
