package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.favoriteRelationship.NewFavoriteRelationshipInput;

public interface CreateFavoriteRelationship {
	FavoriteRelationship handle(NewFavoriteRelationshipInput newFavoriteRelationshipInput);
}
