package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteFavoriteRelationship;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationshipRepository;


@AllArgsConstructor
public class DeleteFavoriteRelationshipImpl implements DeleteFavoriteRelationship {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;

  	@Override
	public boolean handle(String articleId, String userId) {
		return favoriteRelationshipRepository.delete(articleId, userId);
	}
}
