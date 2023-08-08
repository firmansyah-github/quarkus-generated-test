package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindCommentsByFilter;
import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.comments.CommentsRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindCommentsByFilterImpl implements FindCommentsByFilter {

	private final CommentsRepository commentsRepository;

	@Override
	public PageResult<Comments> handle(ResourceFilter resourceFilterr) {
		return commentsRepository.findCommentsByFilter(resourceFilterr);
	}
}
