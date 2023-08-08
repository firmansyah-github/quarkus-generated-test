package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteArticles;
import org.example.realworldapi.domain.model.articles.ArticlesRepository;


@AllArgsConstructor
public class DeleteArticlesImpl implements DeleteArticles {

	private final ArticlesRepository articlesRepository;

  	@Override
	public boolean handle(String id) {
		return articlesRepository.delete(id);
	}
}
