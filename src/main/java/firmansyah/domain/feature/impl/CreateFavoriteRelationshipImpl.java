// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateFavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.*;
import firmansyah.domain.exception.FavoriteRelationshipAlreadyExistsException;
import firmansyah.domain.feature.*;



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
