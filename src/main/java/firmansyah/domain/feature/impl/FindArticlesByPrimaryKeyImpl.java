// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindArticlesByPrimaryKey;
import firmansyah.domain.exception.ArticlesNotFoundException;
import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.ArticlesRepository;


@AllArgsConstructor
public class FindArticlesByPrimaryKeyImpl implements FindArticlesByPrimaryKey {

	private final ArticlesRepository articlesRepository;

	@Override
	public Articles handle(String id) {
		return articlesRepository.findArticlesByPrimaryKey(id)
    			.orElseThrow(ArticlesNotFoundException::new);
	}
}
