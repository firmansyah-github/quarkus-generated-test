package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.users.UsersModelBuilder;
import org.example.realworldapi.domain.model.users.UsersRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Dependent
public class UsersConfiguration {

	@Produces
  	@Singleton
  	public CreateUsers createUsers(
		UsersRepository usersRepository,
      	UsersModelBuilder usersBuilder ) {
    	return new CreateUsersImpl(
        	usersRepository,
        	usersBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteUsers deleteUsers(
		UsersRepository usersRepository) {
    		return new DeleteUsersImpl(
        			usersRepository);
  	}

  	@Produces
  	@Singleton
  	public FindUsersByFilter findUsersByFilter(UsersRepository usersRepository) {
    	return new FindUsersByFilterImpl(usersRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindUsersByPrimaryKey findUsersByPrimaryKey(UsersRepository usersRepository) {
		return new FindUsersByPrimaryKeyImpl(usersRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateUsers updateUsers(
		UsersRepository usersRepository,
      	UsersModelBuilder usersBuilder,
      	FindUsersByPrimaryKey findUsersByPrimaryKey	) {
		return new UpdateUsersImpl(
        	usersRepository,
        	usersBuilder,
        	findUsersByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public UsersModelBuilder usersBuilder(ModelValidator modelValidator) {
		return new UsersModelBuilder(modelValidator);
  	}
}
