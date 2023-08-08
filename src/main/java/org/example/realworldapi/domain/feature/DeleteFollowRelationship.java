package org.example.realworldapi.domain.feature;


public interface DeleteFollowRelationship {
	boolean handle(String followedId, String userId);
}
