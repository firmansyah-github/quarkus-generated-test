package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.articles.Articles;
import java.util.List;



public interface FindArticlesByForeignKey {
  
	List<Articles> handleForAuthorId(java.lang.String AuthorId);
}

