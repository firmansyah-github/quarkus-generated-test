// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.UpdateFavoriteRelationshipInput;


public interface UpdateFavoriteRelationship {
	FavoriteRelationship handle(UpdateFavoriteRelationshipInput updateFavoriteRelationshipInput);
}
