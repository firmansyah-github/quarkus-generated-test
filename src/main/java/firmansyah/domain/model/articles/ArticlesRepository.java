// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.articles;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface ArticlesRepository {

	void save(Articles articles);

	Optional<Articles> findArticlesByPrimaryKey(String id);

	void update(Articles articles);

	boolean delete(String id);

    PageResult<Articles> findArticlesByFilter(ResourceFilter resourceFilter);
    
	long countArticles();
}
