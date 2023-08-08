package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindTagRelationshipByFilter;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindTagRelationshipByFilterImpl implements FindTagRelationshipByFilter {

	private final TagRelationshipRepository tagRelationshipRepository;

	@Override
	public PageResult<TagRelationship> handle(ResourceFilter resourceFilterr) {
		return tagRelationshipRepository.findTagRelationshipByFilter(resourceFilterr);
	}
}
