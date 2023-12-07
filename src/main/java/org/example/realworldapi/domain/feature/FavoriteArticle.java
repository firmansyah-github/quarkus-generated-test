// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.article.FavoriteRelationship;



public interface FavoriteArticle {
  FavoriteRelationship handle(String articleSlug, String currentUserId);
}
