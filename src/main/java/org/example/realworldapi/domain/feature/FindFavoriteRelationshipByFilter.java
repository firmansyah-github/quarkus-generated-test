package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindFavoriteRelationshipByFilter {
	PageResult<FavoriteRelationship> handle(ResourceFilter resourceFilter);
}