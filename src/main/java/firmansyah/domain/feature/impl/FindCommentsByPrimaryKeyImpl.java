// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
