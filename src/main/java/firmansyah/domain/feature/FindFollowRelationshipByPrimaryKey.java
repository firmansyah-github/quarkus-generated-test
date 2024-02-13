// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
            






public interface FindFollowRelationshipByPrimaryKey {
	FollowRelationship handle(String followedId, String userId);
}

