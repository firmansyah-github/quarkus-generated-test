// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.articles.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateArticlesImpl implements UpdateArticles {

	private final ArticlesRepository articlesRepository;
	private final ArticlesModelBuilder articlesBuilder;
	private final FindArticlesByPrimaryKey findArticlesByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	

	@Override
	public Articles handle(UpdateArticlesInput updateArticlesInput) {
		var articles = findArticlesByPrimaryKey.handle(updateArticlesInput.getId());
		
    	articles =
			articlesBuilder.build(updateArticlesInput.getCreatedat(),
					updateArticlesInput.getUpdatedat(),
					updateArticlesInput.getBody(),
					updateArticlesInput.getDescription(),
					updateArticlesInput.getId(),
					updateArticlesInput.getSlug(),
					updateArticlesInput.getTitle(),
					null,
					null,
					null,
					findUsersAuthorIdByPrimaryKey.handle(updateArticlesInput.getAuthorId()));
		articlesRepository.update(articles);
    
		return articles;
	}
}
