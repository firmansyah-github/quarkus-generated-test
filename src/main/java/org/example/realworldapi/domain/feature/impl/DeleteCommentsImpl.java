package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteComments;
import org.example.realworldapi.domain.model.comments.CommentsRepository;


@AllArgsConstructor
public class DeleteCommentsImpl implements DeleteComments {

	private final CommentsRepository commentsRepository;

  	@Override
	public boolean handle(String id) {
		return commentsRepository.delete(id);
	}
}
