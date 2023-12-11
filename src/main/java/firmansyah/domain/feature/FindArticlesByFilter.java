// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindArticlesByFilter {
	PageResult<Articles> handle(ResourceFilter resourceFilter);
}