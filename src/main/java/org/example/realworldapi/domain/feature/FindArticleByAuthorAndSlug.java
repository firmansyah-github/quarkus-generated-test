// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.article.Article;



public interface FindArticleByAuthorAndSlug {
  Article handle(String authorId, String slug);
}
