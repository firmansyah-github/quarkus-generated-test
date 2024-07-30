// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateArticles;
import firmansyah.domain.model.articles.*;
import firmansyah.domain.exception.ArticlesAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateArticlesImpl implements CreateArticles {

	private final ArticlesRepository articlesRepository;
	private final ArticlesModelBuilder articlesBuilder;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	

	@Override
	public Articles handle(NewArticlesInput newArticlesInput) {
		final var articles =
			articlesBuilder.build(newArticlesInput.getCreatedat(),
					newArticlesInput.getUpdatedat(),
					newArticlesInput.getBody(),
					newArticlesInput.getDescription(),
					newArticlesInput.getId(),
					newArticlesInput.getSlug(),
					newArticlesInput.getTitle(),
					null,
					null,
					null,
					findUsersAuthorIdByPrimaryKey.handle(newArticlesInput.getAuthorId()));
		
		if(articlesRepository.findArticlesByPrimaryKey(articles.getId()).isPresent()) {
			throw new ArticlesAlreadyExistsException();
		} else {
			articlesRepository.save(articles);
		}
   
		return articles;
	}
}
