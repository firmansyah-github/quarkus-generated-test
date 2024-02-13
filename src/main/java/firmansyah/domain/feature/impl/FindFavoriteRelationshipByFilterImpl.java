// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindFavoriteRelationshipByFilter;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindFavoriteRelationshipByFilterImpl implements FindFavoriteRelationshipByFilter {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;

	@Override
	public PageResult<FavoriteRelationship> handle(ResourceFilter resourceFilterr) {
		return favoriteRelationshipRepository.findFavoriteRelationshipByFilter(resourceFilterr);
	}
}
