// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteComments;
import firmansyah.domain.model.comments.CommentsRepository;


@AllArgsConstructor
public class DeleteCommentsImpl implements DeleteComments {

	private final CommentsRepository commentsRepository;

  	@Override
	public boolean handle(String id) {
		return commentsRepository.delete(id);
	}
}
