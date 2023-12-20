// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.model.tags;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface TagsRepository {

	void save(Tags tags);

	Optional<Tags> findTagsByPrimaryKey(String id);

	void update(Tags tags);

	boolean delete(String id);

    PageResult<Tags> findTagsByFilter(ResourceFilter resourceFilter);
    
	long countTags();
}
