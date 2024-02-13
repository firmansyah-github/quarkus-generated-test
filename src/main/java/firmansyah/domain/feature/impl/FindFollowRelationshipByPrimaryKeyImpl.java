// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindFollowRelationshipByPrimaryKey;
import firmansyah.domain.exception.FollowRelationshipNotFoundException;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationshipRepository;


@AllArgsConstructor
public class FindFollowRelationshipByPrimaryKeyImpl implements FindFollowRelationshipByPrimaryKey {

	private final FollowRelationshipRepository followRelationshipRepository;

	@Override
	public FollowRelationship handle(String followedId, String userId) {
		return followRelationshipRepository.findFollowRelationshipByPrimaryKey(followedId, userId)
    			.orElseThrow(FollowRelationshipNotFoundException::new);
	}
}
