// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.followRelationship.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

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
