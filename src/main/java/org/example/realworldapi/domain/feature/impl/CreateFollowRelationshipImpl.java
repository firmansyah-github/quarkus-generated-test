package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateFollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.*;
import org.example.realworldapi.domain.exception.FollowRelationshipAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateFollowRelationshipImpl implements CreateFollowRelationship {

	private final FollowRelationshipRepository followRelationshipRepository;
	private final FollowRelationshipModelBuilder followRelationshipBuilder;
	private final FindUsersByPrimaryKey findUsersFollowedIdByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersUserIdByPrimaryKey;
	

	@Override
	public FollowRelationship handle(NewFollowRelationshipInput newFollowRelationshipInput) {
		final var followRelationship =
			followRelationshipBuilder.build(findUsersFollowedIdByPrimaryKey.handle(newFollowRelationshipInput.getFollowedId()),
					findUsersUserIdByPrimaryKey.handle(newFollowRelationshipInput.getUserId()));
		
		if(followRelationshipRepository.findFollowRelationshipByPrimaryKey(followRelationship.getUsersFollowedId().getId(), followRelationship.getUsersUserId().getId()).isPresent()) {
			throw new FollowRelationshipAlreadyExistsException();
		} else {
			followRelationshipRepository.save(followRelationship);
		}
   
		return followRelationship;
	}
}
