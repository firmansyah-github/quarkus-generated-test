// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FavoriteArticle;
import org.example.realworldapi.domain.feature.FindArticleBySlug;
import org.example.realworldapi.domain.feature.FindUserById;
import org.example.realworldapi.domain.model.article.Article;
import org.example.realworldapi.domain.model.article.FavoriteRelationship;
import org.example.realworldapi.domain.model.article.FavoriteRelationshipRepository;



@AllArgsConstructor
public class FavoriteArticleImpl implements FavoriteArticle {

  private final FindArticleBySlug findArticleBySlug;
  private final FindUserById findUserById;
  private final FavoriteRelationshipRepository favoriteRelationshipRepository;

  @Override
  public FavoriteRelationship handle(String articleSlug, String currentUserId) {
    final var article = findArticleBySlug.handle(articleSlug);
    final var favoriteRelationshipOptional =
        favoriteRelationshipRepository.findByArticleIdAndUserId(article.getId(), currentUserId);
    return favoriteRelationshipOptional.orElse(createFavoriteRelationship(currentUserId, article));
  }

  private FavoriteRelationship createFavoriteRelationship(String currentUserId, Article article) {
    final var user = findUserById.handle(currentUserId);
    final var favoriteRelationship = new FavoriteRelationship(user, article);
    favoriteRelationshipRepository.save(favoriteRelationship);
    return favoriteRelationship;
  }
}
