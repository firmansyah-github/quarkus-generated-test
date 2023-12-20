// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.article.Article;



public interface FindArticleByAuthorAndSlug {
  Article handle(String authorId, String slug);
}
