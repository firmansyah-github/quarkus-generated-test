// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindTagRelationshipByFilter;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.tagRelationship.TagRelationshipRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindTagRelationshipByFilterImpl implements FindTagRelationshipByFilter {

	private final TagRelationshipRepository tagRelationshipRepository;

	@Override
	public PageResult<TagRelationship> handle(ResourceFilter resourceFilterr) {
		return tagRelationshipRepository.findTagRelationshipByFilter(resourceFilterr);
	}
}
