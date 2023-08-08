package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.articles.Articles;
import org.example.realworldapi.domain.model.articles.UpdateArticlesInput;


public interface UpdateArticles {
	Articles handle(UpdateArticlesInput updateArticlesInput);
}
