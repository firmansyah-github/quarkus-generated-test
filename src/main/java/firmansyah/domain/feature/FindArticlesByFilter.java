// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindArticlesByFilter {
	PageResult<Articles> handle(ResourceFilter resourceFilter);
}