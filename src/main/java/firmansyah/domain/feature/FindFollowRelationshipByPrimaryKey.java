// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
            






public interface FindFollowRelationshipByPrimaryKey {
	FollowRelationship handle(String followedId, String userId);
}

