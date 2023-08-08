package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
            






public interface FindFavoriteRelationshipByPrimaryKey {
	FavoriteRelationship handle(String articleId, String userId);
}

