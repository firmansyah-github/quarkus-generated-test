// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.users.UsersModelBuilder;
import firmansyah.domain.model.users.UsersRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

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
