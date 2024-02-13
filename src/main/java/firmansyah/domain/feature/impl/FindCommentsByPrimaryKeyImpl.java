// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCommentsByPrimaryKey;
import firmansyah.domain.exception.CommentsNotFoundException;
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.CommentsRepository;


@AllArgsConstructor
public class FindCommentsByPrimaryKeyImpl implements FindCommentsByPrimaryKey {

	private final CommentsRepository commentsRepository;

	@Override
	public Comments handle(String id) {
		return commentsRepository.findCommentsByPrimaryKey(id)
    			.orElseThrow(CommentsNotFoundException::new);
	}
}
