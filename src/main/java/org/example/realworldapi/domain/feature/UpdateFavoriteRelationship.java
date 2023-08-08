package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.favoriteRelationship.UpdateFavoriteRelationshipInput;


public interface UpdateFavoriteRelationship {
	FavoriteRelationship handle(UpdateFavoriteRelationshipInput updateFavoriteRelationshipInput);
}
