// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.NewFavoriteRelationshipInput;

public interface CreateFavoriteRelationship {
	FavoriteRelationship handle(NewFavoriteRelationshipInput newFavoriteRelationshipInput);
}
