// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.favoriteRelationship.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

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
