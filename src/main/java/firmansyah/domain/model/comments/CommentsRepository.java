// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.comments;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface CommentsRepository {

	void save(Comments comments);

	Optional<Comments> findCommentsByPrimaryKey(String id);

	void update(Comments comments);

	boolean delete(String id);

    PageResult<Comments> findCommentsByFilter(ResourceFilter resourceFilter);
    
	long countComments();
}
