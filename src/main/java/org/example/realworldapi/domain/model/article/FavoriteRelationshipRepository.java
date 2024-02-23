// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.article;

import java.util.Optional;


public interface FavoriteRelationshipRepository {
  boolean isFavorited(Article article, String currentUserId);

  long favoritesCount(Article article);

  Optional<FavoriteRelationship> findByArticleIdAndUserId(String articleId, String currentUserId);

  void save(FavoriteRelationship favoriteRelationship);

  void delete(FavoriteRelationship favoriteRelationship);
}
