// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.article.FavoriteRelationship;



public interface FavoriteArticle {
  FavoriteRelationship handle(String articleSlug, String currentUserId);
}
