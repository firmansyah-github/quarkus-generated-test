package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindFollowRelationshipByFilter;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationshipRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindFollowRelationshipByFilterImpl implements FindFollowRelationshipByFilter {

	private final FollowRelationshipRepository followRelationshipRepository;

	@Override
	public PageResult<FollowRelationship> handle(ResourceFilter resourceFilterr) {
		return followRelationshipRepository.findFollowRelationshipByFilter(resourceFilterr);
	}
}
