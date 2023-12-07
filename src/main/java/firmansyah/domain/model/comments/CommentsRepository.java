// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
