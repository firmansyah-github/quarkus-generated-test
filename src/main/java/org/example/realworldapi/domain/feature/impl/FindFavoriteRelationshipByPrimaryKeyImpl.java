package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindFavoriteRelationshipByPrimaryKey;
import org.example.realworldapi.domain.exception.FavoriteRelationshipNotFoundException;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationshipRepository;


@AllArgsConstructor
public class FindFavoriteRelationshipByPrimaryKeyImpl implements FindFavoriteRelationshipByPrimaryKey {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;

	@Override
	public FavoriteRelationship handle(String articleId, String userId) {
		return favoriteRelationshipRepository.findFavoriteRelationshipByPrimaryKey(articleId, userId)
    			.orElseThrow(FavoriteRelationshipNotFoundException::new);
	}
}
