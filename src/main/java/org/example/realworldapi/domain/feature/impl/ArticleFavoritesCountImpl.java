// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
