package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.followRelationship.*;
import org.example.realworldapi.domain.feature.*;

@AllArgsConstructor
public class UpdateFollowRelationshipImpl implements UpdateFollowRelationship {

	private final FollowRelationshipRepository followRelationshipRepository;
	private final FollowRelationshipModelBuilder followRelationshipBuilder;
	private final FindFollowRelationshipByPrimaryKey findFollowRelationshipByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersFollowedIdByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersUserIdByPrimaryKey;
	

	@Override
	public FollowRelationship handle(UpdateFollowRelationshipInput updateFollowRelationshipInput) {
		var followRelationship = findFollowRelationshipByPrimaryKey.handle(updateFollowRelationshipInput.getFollowedId(), updateFollowRelationshipInput.getUserId());
		
    	followRelationship =
			followRelationshipBuilder.build(findUsersFollowedIdByPrimaryKey.handle(updateFollowRelationshipInput.getFollowedId()),
					findUsersUserIdByPrimaryKey.handle(updateFollowRelationshipInput.getUserId()));
		followRelationshipRepository.update(followRelationship);
    
		return followRelationship;
	}
}
