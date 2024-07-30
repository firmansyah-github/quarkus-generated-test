// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import java.util.List;



public interface FindFollowRelationshipByForeignKey {
  
	List<FollowRelationship> handleForFollowedId(java.lang.String FollowedId);
	List<FollowRelationship> handleForUserId(java.lang.String UserId);
}

