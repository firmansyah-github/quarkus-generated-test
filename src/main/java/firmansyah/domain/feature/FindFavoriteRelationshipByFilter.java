// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindFavoriteRelationshipByFilter {
	PageResult<FavoriteRelationship> handle(ResourceFilter resourceFilter);
}