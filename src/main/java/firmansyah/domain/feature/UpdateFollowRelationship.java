// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.UpdateFollowRelationshipInput;


public interface UpdateFollowRelationship {
	FollowRelationship handle(UpdateFollowRelationshipInput updateFollowRelationshipInput);
}
