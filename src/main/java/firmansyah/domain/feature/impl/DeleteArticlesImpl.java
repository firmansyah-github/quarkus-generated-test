// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
