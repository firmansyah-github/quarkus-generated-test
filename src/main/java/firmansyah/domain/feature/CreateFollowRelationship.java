// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.NewFollowRelationshipInput;

public interface CreateFollowRelationship {
	FollowRelationship handle(NewFollowRelationshipInput newFollowRelationshipInput);
}
