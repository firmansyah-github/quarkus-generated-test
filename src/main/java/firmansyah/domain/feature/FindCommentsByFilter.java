// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindCommentsByFilter {
	PageResult<Comments> handle(ResourceFilter resourceFilter);
}