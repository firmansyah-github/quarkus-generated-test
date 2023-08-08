package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindTagsByFilter {
	PageResult<Tags> handle(ResourceFilter resourceFilter);
}