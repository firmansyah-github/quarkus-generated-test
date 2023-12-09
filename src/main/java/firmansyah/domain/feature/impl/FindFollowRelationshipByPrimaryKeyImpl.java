// created by the factor : Dec 9, 2023, 9:19:14 AM  
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