// created by the factor : Feb 23, 2024, 6:45:22 AM  
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
