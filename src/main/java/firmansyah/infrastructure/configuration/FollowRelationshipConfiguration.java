// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.followRelationship.FollowRelationshipModelBuilder;
import firmansyah.domain.model.followRelationship.FollowRelationshipRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class FollowRelationshipConfiguration {

	@Produces
  	@Singleton
  	public CreateFollowRelationship createFollowRelationship(
		FollowRelationshipRepository followRelationshipRepository,
      	FollowRelationshipModelBuilder followRelationshipBuilder ,FindUsersByPrimaryKey findUsersFollowedIdByPrimaryKey,FindUsersByPrimaryKey findUsersUserIdByPrimaryKey) {
    	return new CreateFollowRelationshipImpl(
        	followRelationshipRepository,
        	followRelationshipBuilder ,findUsersFollowedIdByPrimaryKey,findUsersUserIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteFollowRelationship deleteFollowRelationship(
		FollowRelationshipRepository followRelationshipRepository) {
    		return new DeleteFollowRelationshipImpl(
        			followRelationshipRepository);
  	}

  	@Produces
  	@Singleton
  	public FindFollowRelationshipByFilter findFollowRelationshipByFilter(FollowRelationshipRepository followRelationshipRepository) {
    	return new FindFollowRelationshipByFilterImpl(followRelationshipRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindFollowRelationshipByPrimaryKey findFollowRelationshipByPrimaryKey(FollowRelationshipRepository followRelationshipRepository) {
		return new FindFollowRelationshipByPrimaryKeyImpl(followRelationshipRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateFollowRelationship updateFollowRelationship(
		FollowRelationshipRepository followRelationshipRepository,
      	FollowRelationshipModelBuilder followRelationshipBuilder,
      	FindFollowRelationshipByPrimaryKey findFollowRelationshipByPrimaryKey	,FindUsersByPrimaryKey findUsersFollowedIdByPrimaryKey,FindUsersByPrimaryKey findUsersUserIdByPrimaryKey) {
		return new UpdateFollowRelationshipImpl(
        	followRelationshipRepository,
        	followRelationshipBuilder,
        	findFollowRelationshipByPrimaryKey ,findUsersFollowedIdByPrimaryKey,findUsersUserIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public FollowRelationshipModelBuilder followRelationshipBuilder(ModelValidator modelValidator) {
		return new FollowRelationshipModelBuilder(modelValidator);
  	}
}
