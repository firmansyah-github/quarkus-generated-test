// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.user;

import java.util.Optional;


public interface FollowRelationshipRepository {

  boolean isFollowing(String currentUserId, String followedUserId);

  void save(FollowRelationship followRelationship);

  Optional<FollowRelationship> findByUsers(User loggedUser, User followedUser);

  void remove(FollowRelationship followRelationship);
}
