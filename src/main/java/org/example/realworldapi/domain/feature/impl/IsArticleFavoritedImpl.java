// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.IsArticleFavorited;
import org.example.realworldapi.domain.model.article.Article;
import org.example.realworldapi.domain.model.article.FavoriteRelationshipRepository;



@AllArgsConstructor
public class IsArticleFavoritedImpl implements IsArticleFavorited {

  private final FavoriteRelationshipRepository favoriteRelationshipRepository;

  @Override
  public boolean handle(Article article, String currentUserId) {
    return favoriteRelationshipRepository.isFavorited(article, currentUserId);
  }
}
