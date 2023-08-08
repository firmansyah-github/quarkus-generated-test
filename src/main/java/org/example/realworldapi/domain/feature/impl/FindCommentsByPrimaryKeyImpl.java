package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindCommentsByPrimaryKey;
import org.example.realworldapi.domain.exception.CommentsNotFoundException;
import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.domain.model.comments.CommentsRepository;


@AllArgsConstructor
public class FindCommentsByPrimaryKeyImpl implements FindCommentsByPrimaryKey {

	private final CommentsRepository commentsRepository;

	@Override
	public Comments handle(String id) {
		return commentsRepository.findCommentsByPrimaryKey(id)
    			.orElseThrow(CommentsNotFoundException::new);
	}
}
