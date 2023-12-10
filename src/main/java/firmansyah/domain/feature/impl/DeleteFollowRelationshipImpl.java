// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteFollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationshipRepository;


@AllArgsConstructor
public class DeleteFollowRelationshipImpl implements DeleteFollowRelationship {

	private final FollowRelationshipRepository followRelationshipRepository;

  	@Override
	public boolean handle(String followedId, String userId) {
		return followRelationshipRepository.delete(followedId, userId);
	}
}
