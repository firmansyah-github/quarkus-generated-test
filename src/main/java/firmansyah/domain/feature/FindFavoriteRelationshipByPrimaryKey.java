// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
            






public interface FindFavoriteRelationshipByPrimaryKey {
	FavoriteRelationship handle(String articleId, String userId);
}

