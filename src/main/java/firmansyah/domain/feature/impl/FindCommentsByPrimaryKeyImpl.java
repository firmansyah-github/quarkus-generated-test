// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
