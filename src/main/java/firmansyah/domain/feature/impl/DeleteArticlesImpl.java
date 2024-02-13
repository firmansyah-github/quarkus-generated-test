// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
