// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.model.article;

import java.util.Optional;


public interface FavoriteRelationshipRepository {
  boolean isFavorited(Article article, String currentUserId);

  long favoritesCount(Article article);

  Optional<FavoriteRelationship> findByArticleIdAndUserId(String articleId, String currentUserId);

  void save(FavoriteRelationship favoriteRelationship);

  void delete(FavoriteRelationship favoriteRelationship);
}
