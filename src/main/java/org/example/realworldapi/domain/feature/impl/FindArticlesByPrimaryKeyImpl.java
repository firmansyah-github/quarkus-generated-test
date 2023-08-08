package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindArticlesByPrimaryKey;
import org.example.realworldapi.domain.exception.ArticlesNotFoundException;
import org.example.realworldapi.domain.model.articles.Articles;
import org.example.realworldapi.domain.model.articles.ArticlesRepository;


@AllArgsConstructor
public class FindArticlesByPrimaryKeyImpl implements FindArticlesByPrimaryKey {

	private final ArticlesRepository articlesRepository;

	@Override
	public Articles handle(String id) {
		return articlesRepository.findArticlesByPrimaryKey(id)
    			.orElseThrow(ArticlesNotFoundException::new);
	}
}
