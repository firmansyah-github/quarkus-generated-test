// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
