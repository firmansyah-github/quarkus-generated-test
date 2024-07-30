// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.comments.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateCommentsImpl implements UpdateComments {

	private final CommentsRepository commentsRepository;
	private final CommentsModelBuilder commentsBuilder;
	private final FindCommentsByPrimaryKey findCommentsByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	

	@Override
	public Comments handle(UpdateCommentsInput updateCommentsInput) {
		var comments = findCommentsByPrimaryKey.handle(updateCommentsInput.getId());
		
    	comments =
			commentsBuilder.build(updateCommentsInput.getCreatedat(),
					updateCommentsInput.getUpdatedat(),
					updateCommentsInput.getBody(),
					updateCommentsInput.getId(),
					findArticlesArticleIdByPrimaryKey.handle(updateCommentsInput.getArticleId()),
					findUsersAuthorIdByPrimaryKey.handle(updateCommentsInput.getAuthorId()));
		commentsRepository.update(comments);
    
		return comments;
	}
}
