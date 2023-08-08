package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateComments;
import org.example.realworldapi.domain.model.comments.*;
import org.example.realworldapi.domain.exception.CommentsAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateCommentsImpl implements CreateComments {

	private final CommentsRepository commentsRepository;
	private final CommentsModelBuilder commentsBuilder;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public Comments handle(NewCommentsInput newCommentsInput) {
		final var comments =
			commentsBuilder.build(newCommentsInput.getId(),
					newCommentsInput.getBody(),
					newCommentsInput.getCreatedat(),
					newCommentsInput.getUpdatedat(),
					findUsersAuthorIdByPrimaryKey.handle(newCommentsInput.getAuthorId()),
					findArticlesArticleIdByPrimaryKey.handle(newCommentsInput.getArticleId()));
		
		if(commentsRepository.findCommentsByPrimaryKey(comments.getId()).isPresent()) {
			throw new CommentsAlreadyExistsException();
		} else {
			commentsRepository.save(comments);
		}
   
		return comments;
	}
}
