// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.article.FavoriteRelationship;



public interface FavoriteArticle {
  FavoriteRelationship handle(String articleSlug, String currentUserId);
}
