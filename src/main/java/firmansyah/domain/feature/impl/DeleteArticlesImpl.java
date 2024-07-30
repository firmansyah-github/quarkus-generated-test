// created by the factor : May 30, 2024, 6:48:44 AM  
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
