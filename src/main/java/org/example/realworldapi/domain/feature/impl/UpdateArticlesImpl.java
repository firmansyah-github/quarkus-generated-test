package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.articles.*;
import org.example.realworldapi.domain.feature.*;

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
			articlesBuilder.build(updateArticlesInput.getId(),
					updateArticlesInput.getBody(),
					updateArticlesInput.getDescription(),
					updateArticlesInput.getSlug(),
					updateArticlesInput.getTitle(),
					updateArticlesInput.getUpdatedat(),
					null,
					null,
					null,
					findUsersAuthorIdByPrimaryKey.handle(updateArticlesInput.getAuthorId()));
		articlesRepository.update(articles);
    
		return articles;
	}
}
