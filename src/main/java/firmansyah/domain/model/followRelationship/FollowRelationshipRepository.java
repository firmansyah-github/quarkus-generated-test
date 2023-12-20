// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.model.followRelationship;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface FollowRelationshipRepository {

	void save(FollowRelationship followRelationship);

	Optional<FollowRelationship> findFollowRelationshipByPrimaryKey(String followedId,String userId);

	void update(FollowRelationship followRelationship);

	boolean delete(String followedId,String userId);

    PageResult<FollowRelationship> findFollowRelationshipByFilter(ResourceFilter resourceFilter);
    
	long countFollowRelationship();
}
