// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.NewFollowRelationshipInput;

public interface CreateFollowRelationship {
	FollowRelationship handle(NewFollowRelationshipInput newFollowRelationshipInput);
}
