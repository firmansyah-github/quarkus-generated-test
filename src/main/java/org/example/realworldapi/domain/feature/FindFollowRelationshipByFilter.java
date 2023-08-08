package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindFollowRelationshipByFilter {
	PageResult<FollowRelationship> handle(ResourceFilter resourceFilter);
}