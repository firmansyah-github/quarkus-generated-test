package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindArticlesByFilter;
import org.example.realworldapi.domain.model.articles.Articles;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.articles.ArticlesRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindArticlesByFilterImpl implements FindArticlesByFilter {

	private final ArticlesRepository articlesRepository;

	@Override
	public PageResult<Articles> handle(ResourceFilter resourceFilterr) {
		return articlesRepository.findArticlesByFilter(resourceFilterr);
	}
}
