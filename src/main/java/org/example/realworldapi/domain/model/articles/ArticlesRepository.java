package org.example.realworldapi.domain.model.articles;

import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import org.example.realworldapi.domain.model.util.PageResult;


public interface ArticlesRepository {

	void save(Articles articles);

	Optional<Articles> findArticlesByPrimaryKey(String id);

	void update(Articles articles);

	boolean delete(String id);

    PageResult<Articles> findArticlesByFilter(ResourceFilter resourceFilter);
    
	long countArticles();
}
