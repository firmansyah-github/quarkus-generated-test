// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipModelBuilder;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

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
