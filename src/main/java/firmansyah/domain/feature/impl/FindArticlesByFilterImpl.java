// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindArticlesByFilter;
import firmansyah.domain.model.articles.Articles;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.articles.ArticlesRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindArticlesByFilterImpl implements FindArticlesByFilter {

	private final ArticlesRepository articlesRepository;

	@Override
	public PageResult<Articles> handle(ResourceFilter resourceFilterr) {
		return articlesRepository.findArticlesByFilter(resourceFilterr);
	}
}
