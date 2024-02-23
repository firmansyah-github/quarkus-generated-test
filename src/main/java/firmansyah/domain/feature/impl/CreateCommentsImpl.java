// created by the factor : Feb 23, 2024, 6:45:22 AM  
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
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public Comments handle(NewCommentsInput newCommentsInput) {
		final var comments =
			commentsBuilder.build(newCommentsInput.getCreatedat(),
					newCommentsInput.getUpdatedat(),
					newCommentsInput.getBody(),
					newCommentsInput.getId(),
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
