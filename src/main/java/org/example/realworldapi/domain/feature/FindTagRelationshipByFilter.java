package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindTagRelationshipByFilter {
	PageResult<TagRelationship> handle(ResourceFilter resourceFilter);
}