// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindFavoriteRelationshipByPrimaryKey;
import firmansyah.domain.exception.FavoriteRelationshipNotFoundException;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipRepository;


@AllArgsConstructor
public class FindFavoriteRelationshipByPrimaryKeyImpl implements FindFavoriteRelationshipByPrimaryKey {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;

	@Override
	public FavoriteRelationship handle(String articleId, String userId) {
		return favoriteRelationshipRepository.findFavoriteRelationshipByPrimaryKey(articleId, userId)
    			.orElseThrow(FavoriteRelationshipNotFoundException::new);
	}
}
