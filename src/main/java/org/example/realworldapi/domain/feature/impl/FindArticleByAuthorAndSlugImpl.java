// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindArticleByAuthorAndSlug;
import org.example.realworldapi.domain.model.article.Article;
import org.example.realworldapi.domain.model.article.ArticleRepository;
import org.example.realworldapi.domain.exception.ArticleNotFoundException;



@AllArgsConstructor
public class FindArticleByAuthorAndSlugImpl implements FindArticleByAuthorAndSlug {

  private final ArticleRepository articleRepository;

  @Override
  public Article handle(String authorId, String slug) {
    return articleRepository
        .findByAuthorAndSlug(authorId, slug)
        .orElseThrow(ArticleNotFoundException::new);
  }
}
