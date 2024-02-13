// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.model.article;

import java.util.Optional;


public interface FavoriteRelationshipRepository {
  boolean isFavorited(Article article, String currentUserId);

  long favoritesCount(Article article);

  Optional<FavoriteRelationship> findByArticleIdAndUserId(String articleId, String currentUserId);

  void save(FavoriteRelationship favoriteRelationship);

  void delete(FavoriteRelationship favoriteRelationship);
}
