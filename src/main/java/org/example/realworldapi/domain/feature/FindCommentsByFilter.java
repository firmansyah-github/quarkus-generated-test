package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindCommentsByFilter {
	PageResult<Comments> handle(ResourceFilter resourceFilter);
}