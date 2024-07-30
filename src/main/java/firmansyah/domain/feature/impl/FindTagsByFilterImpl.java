// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindTagsByFilter;
import firmansyah.domain.model.tags.Tags;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.tags.TagsRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindTagsByFilterImpl implements FindTagsByFilter {

	private final TagsRepository tagsRepository;

	@Override
	public PageResult<Tags> handle(ResourceFilter resourceFilterr) {
		return tagsRepository.findTagsByFilter(resourceFilterr);
	}
}
