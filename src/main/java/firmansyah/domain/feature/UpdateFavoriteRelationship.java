// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.UpdateFavoriteRelationshipInput;


public interface UpdateFavoriteRelationship {
	FavoriteRelationship handle(UpdateFavoriteRelationshipInput updateFavoriteRelationshipInput);
}
