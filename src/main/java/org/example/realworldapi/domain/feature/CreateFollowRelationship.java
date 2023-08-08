package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.NewFollowRelationshipInput;

public interface CreateFollowRelationship {
	FollowRelationship handle(NewFollowRelationshipInput newFollowRelationshipInput);
}
