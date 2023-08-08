package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import java.util.List;



public interface FindFollowRelationshipByForeignKey {
  
	List<FollowRelationship> handleForFollowedId(java.lang.String FollowedId);
	List<FollowRelationship> handleForUserId(java.lang.String UserId);
}

