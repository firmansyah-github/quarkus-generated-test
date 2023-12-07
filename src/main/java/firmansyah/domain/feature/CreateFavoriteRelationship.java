// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.NewFavoriteRelationshipInput;

public interface CreateFavoriteRelationship {
	FavoriteRelationship handle(NewFavoriteRelationshipInput newFavoriteRelationshipInput);
}
