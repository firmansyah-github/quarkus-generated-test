package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.articles.Articles;
            

import java.time.LocalDateTime;





public interface FindArticlesByPrimaryKey {
	Articles handle(String id);
}

