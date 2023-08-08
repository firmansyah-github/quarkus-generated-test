package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.UpdateFollowRelationshipInput;


public interface UpdateFollowRelationship {
	FollowRelationship handle(UpdateFollowRelationshipInput updateFollowRelationshipInput);
}
