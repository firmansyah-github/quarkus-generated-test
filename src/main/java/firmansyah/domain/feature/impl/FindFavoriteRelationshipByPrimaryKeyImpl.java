// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
