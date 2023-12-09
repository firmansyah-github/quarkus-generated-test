// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteArticles;
import firmansyah.domain.model.articles.ArticlesRepository;


@AllArgsConstructor
public class DeleteArticlesImpl implements DeleteArticles {

	private final ArticlesRepository articlesRepository;

  	@Override
	public boolean handle(String id) {
		return articlesRepository.delete(id);
	}
}
