package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindTagsByFilter;
import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.tags.TagsRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindTagsByFilterImpl implements FindTagsByFilter {

	private final TagsRepository tagsRepository;

	@Override
	public PageResult<Tags> handle(ResourceFilter resourceFilterr) {
		return tagsRepository.findTagsByFilter(resourceFilterr);
	}
}
