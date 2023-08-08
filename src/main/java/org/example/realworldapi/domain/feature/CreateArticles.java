package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.articles.Articles;
import org.example.realworldapi.domain.model.articles.NewArticlesInput;

public interface CreateArticles {
	Articles handle(NewArticlesInput newArticlesInput);
}
