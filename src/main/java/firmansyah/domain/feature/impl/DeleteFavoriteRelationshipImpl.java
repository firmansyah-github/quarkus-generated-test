// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteFavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipRepository;


@AllArgsConstructor
public class DeleteFavoriteRelationshipImpl implements DeleteFavoriteRelationship {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;

  	@Override
	public boolean handle(String articleId, String userId) {
		return favoriteRelationshipRepository.delete(articleId, userId);
	}
}
