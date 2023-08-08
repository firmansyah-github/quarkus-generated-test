package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.comments.*;
import org.example.realworldapi.domain.feature.*;

@AllArgsConstructor
public class UpdateCommentsImpl implements UpdateComments {

	private final CommentsRepository commentsRepository;
	private final CommentsModelBuilder commentsBuilder;
	private final FindCommentsByPrimaryKey findCommentsByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public Comments handle(UpdateCommentsInput updateCommentsInput) {
		var comments = findCommentsByPrimaryKey.handle(updateCommentsInput.getId());
		
    	comments =
			commentsBuilder.build(updateCommentsInput.getId(),
					updateCommentsInput.getBody(),
					updateCommentsInput.getCreatedat(),
					updateCommentsInput.getUpdatedat(),
					findUsersAuthorIdByPrimaryKey.handle(updateCommentsInput.getAuthorId()),
					findArticlesArticleIdByPrimaryKey.handle(updateCommentsInput.getArticleId()));
		commentsRepository.update(comments);
    
		return comments;
	}
}
