// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindFollowRelationshipByFilter;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.followRelationship.FollowRelationshipRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindFollowRelationshipByFilterImpl implements FindFollowRelationshipByFilter {

	private final FollowRelationshipRepository followRelationshipRepository;

	@Override
	public PageResult<FollowRelationship> handle(ResourceFilter resourceFilterr) {
		return followRelationshipRepository.findFollowRelationshipByFilter(resourceFilterr);
	}
}
