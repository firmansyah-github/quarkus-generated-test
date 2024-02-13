// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.model.user;

import java.util.Optional;


public interface FollowRelationshipRepository {

  boolean isFollowing(String currentUserId, String followedUserId);

  void save(FollowRelationship followRelationship);

  Optional<FollowRelationship> findByUsers(User loggedUser, User followedUser);

  void remove(FollowRelationship followRelationship);
}
