// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindFollowRelationshipByFilter {
	PageResult<FollowRelationship> handle(ResourceFilter resourceFilter);
}