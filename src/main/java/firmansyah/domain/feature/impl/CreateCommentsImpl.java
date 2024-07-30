// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateComments;
import firmansyah.domain.model.comments.*;
import firmansyah.domain.exception.CommentsAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateCommentsImpl implements CreateComments {

	private final CommentsRepository commentsRepository;
	private final CommentsModelBuilder commentsBuilder;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	

	@Override
	public Comments handle(NewCommentsInput newCommentsInput) {
		final var comments =
			commentsBuilder.build(newCommentsInput.getCreatedat(),
					newCommentsInput.getUpdatedat(),
					newCommentsInput.getBody(),
					newCommentsInput.getId(),
					findArticlesArticleIdByPrimaryKey.handle(newCommentsInput.getArticleId()),
					findUsersAuthorIdByPrimaryKey.handle(newCommentsInput.getAuthorId()));
		
		if(commentsRepository.findCommentsByPrimaryKey(comments.getId()).isPresent()) {
			throw new CommentsAlreadyExistsException();
		} else {
			commentsRepository.save(comments);
		}
   
		return comments;
	}
}
