package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateArticles;
import org.example.realworldapi.domain.model.articles.*;
import org.example.realworldapi.domain.exception.ArticlesAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateArticlesImpl implements CreateArticles {

	private final ArticlesRepository articlesRepository;
	private final ArticlesModelBuilder articlesBuilder;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	

	@Override
	public Articles handle(NewArticlesInput newArticlesInput) {
		final var articles =
			articlesBuilder.build(newArticlesInput.getId(),
					newArticlesInput.getBody(),
					newArticlesInput.getDescription(),
					newArticlesInput.getSlug(),
					newArticlesInput.getTitle(),
					newArticlesInput.getUpdatedat(),
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
