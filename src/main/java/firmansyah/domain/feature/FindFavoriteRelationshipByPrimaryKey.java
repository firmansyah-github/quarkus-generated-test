// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
            






public interface FindFavoriteRelationshipByPrimaryKey {
	FavoriteRelationship handle(String articleId, String userId);
}

