package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.articles.Articles;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindArticlesByFilter {
	PageResult<Articles> handle(ResourceFilter resourceFilter);
}