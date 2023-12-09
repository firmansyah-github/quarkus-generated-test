// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.NewFavoriteRelationshipInput;

public interface CreateFavoriteRelationship {
	FavoriteRelationship handle(NewFavoriteRelationshipInput newFavoriteRelationshipInput);
}
