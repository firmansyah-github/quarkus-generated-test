package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindFavoriteRelationshipByFilter;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationshipRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindFavoriteRelationshipByFilterImpl implements FindFavoriteRelationshipByFilter {

	private final FavoriteRelationshipRepository favoriteRelationshipRepository;

	@Override
	public PageResult<FavoriteRelationship> handle(ResourceFilter resourceFilterr) {
		return favoriteRelationshipRepository.findFavoriteRelationshipByFilter(resourceFilterr);
	}
}
