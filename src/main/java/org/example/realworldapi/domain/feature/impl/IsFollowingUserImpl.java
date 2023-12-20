// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.IsFollowingUser;
import org.example.realworldapi.domain.model.user.FollowRelationshipRepository;



@AllArgsConstructor
public class IsFollowingUserImpl implements IsFollowingUser {

  private final FollowRelationshipRepository usersFollowedRepository;

  @Override
  public boolean handle(String currentUserId, String followedUserId) {
    return usersFollowedRepository.isFollowing(currentUserId, followedUserId);
  }
}
