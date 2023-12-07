// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public Comments handle(UpdateCommentsInput updateCommentsInput) {
		var comments = findCommentsByPrimaryKey.handle(updateCommentsInput.getId());
		
    	comments =
			commentsBuilder.build(updateCommentsInput.getCreatedat(),
					updateCommentsInput.getUpdatedat(),
					updateCommentsInput.getBody(),
					updateCommentsInput.getId(),
					findUsersAuthorIdByPrimaryKey.handle(updateCommentsInput.getAuthorId()),
					findArticlesArticleIdByPrimaryKey.handle(updateCommentsInput.getArticleId()));
		commentsRepository.update(comments);
    
		return comments;
	}
}
