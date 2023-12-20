// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.model.user;

import java.util.Optional;


public interface FollowRelationshipRepository {

  boolean isFollowing(String currentUserId, String followedUserId);

  void save(FollowRelationship followRelationship);

  Optional<FollowRelationship> findByUsers(User loggedUser, User followedUser);

  void remove(FollowRelationship followRelationship);
}
