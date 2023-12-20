// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
