// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
            






public interface FindFavoriteRelationshipByPrimaryKey {
	FavoriteRelationship handle(String articleId, String userId);
}

