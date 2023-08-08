package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationshipModelBuilder;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationshipRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Dependent
public class FavoriteRelationshipConfiguration {

	@Produces
  	@Singleton
  	public CreateFavoriteRelationship createFavoriteRelationship(
		FavoriteRelationshipRepository favoriteRelationshipRepository,
      	FavoriteRelationshipModelBuilder favoriteRelationshipBuilder ,FindUsersByPrimaryKey findUsersUserIdByPrimaryKey,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey) {
    	return new CreateFavoriteRelationshipImpl(
        	favoriteRelationshipRepository,
        	favoriteRelationshipBuilder ,findUsersUserIdByPrimaryKey,findArticlesArticleIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteFavoriteRelationship deleteFavoriteRelationship(
		FavoriteRelationshipRepository favoriteRelationshipRepository) {
    		return new DeleteFavoriteRelationshipImpl(
        			favoriteRelationshipRepository);
  	}

  	@Produces
  	@Singleton
  	public FindFavoriteRelationshipByFilter findFavoriteRelationshipByFilter(FavoriteRelationshipRepository favoriteRelationshipRepository) {
    	return new FindFavoriteRelationshipByFilterImpl(favoriteRelationshipRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindFavoriteRelationshipByPrimaryKey findFavoriteRelationshipByPrimaryKey(FavoriteRelationshipRepository favoriteRelationshipRepository) {
		return new FindFavoriteRelationshipByPrimaryKeyImpl(favoriteRelationshipRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateFavoriteRelationship updateFavoriteRelationship(
		FavoriteRelationshipRepository favoriteRelationshipRepository,
      	FavoriteRelationshipModelBuilder favoriteRelationshipBuilder,
      	FindFavoriteRelationshipByPrimaryKey findFavoriteRelationshipByPrimaryKey	,FindUsersByPrimaryKey findUsersUserIdByPrimaryKey,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey) {
		return new UpdateFavoriteRelationshipImpl(
        	favoriteRelationshipRepository,
        	favoriteRelationshipBuilder,
        	findFavoriteRelationshipByPrimaryKey ,findUsersUserIdByPrimaryKey,findArticlesArticleIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public FavoriteRelationshipModelBuilder favoriteRelationshipBuilder(ModelValidator modelValidator) {
		return new FavoriteRelationshipModelBuilder(modelValidator);
  	}
}
