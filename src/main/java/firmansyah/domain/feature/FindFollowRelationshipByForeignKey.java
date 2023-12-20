// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import java.util.List;



public interface FindFollowRelationshipByForeignKey {
  
	List<FollowRelationship> handleForFollowedId(java.lang.String FollowedId);
	List<FollowRelationship> handleForUserId(java.lang.String UserId);
}

