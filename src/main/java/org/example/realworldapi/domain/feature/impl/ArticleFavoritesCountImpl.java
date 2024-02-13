// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.ArticleFavoritesCount;
import org.example.realworldapi.domain.feature.FindArticleById;
import org.example.realworldapi.domain.model.article.FavoriteRelationshipRepository;



@AllArgsConstructor
public class ArticleFavoritesCountImpl implements ArticleFavoritesCount {

  private final FindArticleById findArticleById;
  private final FavoriteRelationshipRepository favoriteRelationshipRepository;

  @Override
  public long handle(String articleId) {
    final var article = findArticleById.handle(articleId);
    return favoriteRelationshipRepository.favoritesCount(article);
  }
}
