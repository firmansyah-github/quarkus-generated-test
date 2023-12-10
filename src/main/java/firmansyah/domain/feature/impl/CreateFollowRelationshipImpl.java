// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateFollowRelationship;
import firmansyah.domain.model.followRelationship.*;
import firmansyah.domain.exception.FollowRelationshipAlreadyExistsException;
import firmansyah.domain.feature.*;



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
