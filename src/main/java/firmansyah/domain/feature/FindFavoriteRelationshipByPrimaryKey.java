// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
            






public interface FindFavoriteRelationshipByPrimaryKey {
	FavoriteRelationship handle(String articleId, String userId);
}

