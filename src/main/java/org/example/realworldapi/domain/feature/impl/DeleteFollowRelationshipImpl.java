package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteFollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationshipRepository;


@AllArgsConstructor
public class DeleteFollowRelationshipImpl implements DeleteFollowRelationship {

	private final FollowRelationshipRepository followRelationshipRepository;

  	@Override
	public boolean handle(String followedId, String userId) {
		return followRelationshipRepository.delete(followedId, userId);
	}
}
