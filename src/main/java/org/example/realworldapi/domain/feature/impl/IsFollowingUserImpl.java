// modify by the factor : Feb 22, 2024, 10:16:04 PM  
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
