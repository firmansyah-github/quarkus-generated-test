// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
