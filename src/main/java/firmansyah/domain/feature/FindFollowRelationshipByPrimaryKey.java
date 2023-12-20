// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
            






public interface FindFollowRelationshipByPrimaryKey {
	FollowRelationship handle(String followedId, String userId);
}

