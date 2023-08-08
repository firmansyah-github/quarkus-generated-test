package org.example.realworldapi.domain.model.tags;

import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import org.example.realworldapi.domain.model.util.PageResult;


public interface TagsRepository {

	void save(Tags tags);

	Optional<Tags> findTagsByPrimaryKey(String id);

	void update(Tags tags);

	boolean delete(String id);

    PageResult<Tags> findTagsByFilter(ResourceFilter resourceFilter);
    
	long countTags();
}
