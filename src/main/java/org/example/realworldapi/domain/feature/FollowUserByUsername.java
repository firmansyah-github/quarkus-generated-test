// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.user.FollowRelationship;



public interface FollowUserByUsername {
  FollowRelationship handle(String loggedUserId, String username);
}
