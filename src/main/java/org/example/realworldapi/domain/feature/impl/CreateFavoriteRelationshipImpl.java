package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateFavoriteRelationship;
import org.example.realworldapi.domain.model.favoriteRelationship.*;
import org.example.realworldapi.domain.exception.FavoriteRelationshipAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateFavoriteRelationshipImpl implements CreateFavoriteRelationship {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;
	private final FavoriteRelationshipModelBuilder favoriteRelationshipBuilder;
	private final FindUsersByPrimaryKey findUsersUserIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public FavoriteRelationship handle(NewFavoriteRelationshipInput newFavoriteRelationshipInput) {
		final var favoriteRelationship =
			favoriteRelationshipBuilder.build(findUsersUserIdByPrimaryKey.handle(newFavoriteRelationshipInput.getUserId()),
					findArticlesArticleIdByPrimaryKey.handle(newFavoriteRelationshipInput.getArticleId()));
		
		if(favoriteRelationshipRepository.findFavoriteRelationshipByPrimaryKey(favoriteRelationship.getArticlesArticleId().getId(), favoriteRelationship.getUsersUserId().getId()).isPresent()) {
			throw new FavoriteRelationshipAlreadyExistsException();
		} else {
			favoriteRelationshipRepository.save(favoriteRelationship);
		}
   
		return favoriteRelationship;
	}
}
