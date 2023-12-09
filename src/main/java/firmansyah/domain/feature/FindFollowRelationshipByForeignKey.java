// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import java.util.List;



public interface FindFollowRelationshipByForeignKey {
  
	List<FollowRelationship> handleForFollowedId(java.lang.String FollowedId);
	List<FollowRelationship> handleForUserId(java.lang.String UserId);
}

