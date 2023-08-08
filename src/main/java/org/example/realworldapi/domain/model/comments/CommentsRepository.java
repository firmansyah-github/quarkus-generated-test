package org.example.realworldapi.domain.model.comments;

import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import org.example.realworldapi.domain.model.util.PageResult;


public interface CommentsRepository {

	void save(Comments comments);

	Optional<Comments> findCommentsByPrimaryKey(String id);

	void update(Comments comments);

	boolean delete(String id);

    PageResult<Comments> findCommentsByFilter(ResourceFilter resourceFilter);
    
	long countComments();
}
