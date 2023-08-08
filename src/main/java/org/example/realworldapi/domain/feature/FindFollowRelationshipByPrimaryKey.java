package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
            






public interface FindFollowRelationshipByPrimaryKey {
	FollowRelationship handle(String followedId, String userId);
}

