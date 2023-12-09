// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindFollowRelationshipByFilter {
	PageResult<FollowRelationship> handle(ResourceFilter resourceFilter);
}