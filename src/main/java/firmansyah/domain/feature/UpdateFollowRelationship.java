// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.UpdateFollowRelationshipInput;


public interface UpdateFollowRelationship {
	FollowRelationship handle(UpdateFollowRelationshipInput updateFollowRelationshipInput);
}
