package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.favoriteRelationship.*;
import org.example.realworldapi.domain.feature.*;

@AllArgsConstructor
public class UpdateFavoriteRelationshipImpl implements UpdateFavoriteRelationship {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;
	private final FavoriteRelationshipModelBuilder favoriteRelationshipBuilder;
	private final FindFavoriteRelationshipByPrimaryKey findFavoriteRelationshipByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersUserIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public FavoriteRelationship handle(UpdateFavoriteRelationshipInput updateFavoriteRelationshipInput) {
		var favoriteRelationship = findFavoriteRelationshipByPrimaryKey.handle(updateFavoriteRelationshipInput.getArticleId(), updateFavoriteRelationshipInput.getUserId());
		
    	favoriteRelationship =
			favoriteRelationshipBuilder.build(findUsersUserIdByPrimaryKey.handle(updateFavoriteRelationshipInput.getUserId()),
					findArticlesArticleIdByPrimaryKey.handle(updateFavoriteRelationshipInput.getArticleId()));
		favoriteRelationshipRepository.update(favoriteRelationship);
    
		return favoriteRelationship;
	}
}
