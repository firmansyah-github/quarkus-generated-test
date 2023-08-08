package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationshipModelBuilder;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationshipRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

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
