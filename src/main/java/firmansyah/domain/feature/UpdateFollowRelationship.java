// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.UpdateFollowRelationshipInput;


public interface UpdateFollowRelationship {
	FollowRelationship handle(UpdateFollowRelationshipInput updateFollowRelationshipInput);
}
