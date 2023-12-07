// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
