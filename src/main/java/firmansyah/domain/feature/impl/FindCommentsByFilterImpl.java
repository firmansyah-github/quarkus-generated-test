// created by the factor : Dec 11, 2023, 6:10:51 PM  
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
