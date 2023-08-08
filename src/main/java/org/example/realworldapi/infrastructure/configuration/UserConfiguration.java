package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.provider.HashProvider;
import org.example.realworldapi.domain.model.user.UserModelBuilder;
import org.example.realworldapi.domain.model.user.UserRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.validation.Validator;

@Dependent
public class UserConfiguration {

	  @Produces
	  @Singleton
	  public CreateUser createUser(
	      UserRepository userRepository, HashProvider hashProvider, UserModelBuilder userBuilder) {
	    return new CreateUserImpl(userRepository, hashProvider, userBuilder);
	  }
	
	  @Produces
	  @Singleton
	  public UpdateUser updateUser(
	      FindUserById findUserById, UserRepository userRepository, ModelValidator modelValidator) {
	    return new UpdateUserImpl(findUserById, userRepository, modelValidator);
	  }
	
	  @Produces
	  @Singleton
	  public FindUserById findUserById(UserRepository userRepository) {
	    return new FindUserByIdImpl(userRepository);
	  }
	
	  @Produces
	  @Singleton
	  public LoginUser loginUser(UserRepository userRepository, HashProvider hashProvider) {
	    return new LoginUserImpl(userRepository, hashProvider);
	  }
	
	  @Produces
	  @Singleton
	  public FindUserByUsername findUserByUsername(UserRepository userRepository) {
	    return new FindUserByUsernameImpl(userRepository);
	  }
	
	
	  @Produces
	  @Singleton
	  public UserModelBuilder userModelBuilder(ModelValidator modelValidator) {
	    return new UserModelBuilder(modelValidator);
	  }

}
