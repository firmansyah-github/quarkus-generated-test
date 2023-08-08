package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindFollowRelationshipByPrimaryKey;
import org.example.realworldapi.domain.exception.FollowRelationshipNotFoundException;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationshipRepository;


@AllArgsConstructor
public class FindFollowRelationshipByPrimaryKeyImpl implements FindFollowRelationshipByPrimaryKey {

	private final FollowRelationshipRepository followRelationshipRepository;

	@Override
	public FollowRelationship handle(String followedId, String userId) {
		return followRelationshipRepository.findFollowRelationshipByPrimaryKey(followedId, userId)
    			.orElseThrow(FollowRelationshipNotFoundException::new);
	}
}
