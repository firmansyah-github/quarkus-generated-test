// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCommentsByFilter;
import firmansyah.domain.model.comments.Comments;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.comments.CommentsRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindCommentsByFilterImpl implements FindCommentsByFilter {

	private final CommentsRepository commentsRepository;

	@Override
	public PageResult<Comments> handle(ResourceFilter resourceFilterr) {
		return commentsRepository.findCommentsByFilter(resourceFilterr);
	}
}
