// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.usStates.UsStatesModelBuilder;
import firmansyah.domain.model.usStates.UsStatesRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class UsStatesConfiguration {

	@Produces
  	@Singleton
  	public CreateUsStates createUsStates(
		UsStatesRepository usStatesRepository,
      	UsStatesModelBuilder usStatesBuilder ) {
    	return new CreateUsStatesImpl(
        	usStatesRepository,
        	usStatesBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteUsStates deleteUsStates(
		UsStatesRepository usStatesRepository) {
    		return new DeleteUsStatesImpl(
        			usStatesRepository);
  	}

  	@Produces
  	@Singleton
  	public FindUsStatesByFilter findUsStatesByFilter(UsStatesRepository usStatesRepository) {
    	return new FindUsStatesByFilterImpl(usStatesRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindUsStatesByPrimaryKey findUsStatesByPrimaryKey(UsStatesRepository usStatesRepository) {
		return new FindUsStatesByPrimaryKeyImpl(usStatesRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateUsStates updateUsStates(
		UsStatesRepository usStatesRepository,
      	UsStatesModelBuilder usStatesBuilder,
      	FindUsStatesByPrimaryKey findUsStatesByPrimaryKey	) {
		return new UpdateUsStatesImpl(
        	usStatesRepository,
        	usStatesBuilder,
        	findUsStatesByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public UsStatesModelBuilder usStatesBuilder(ModelValidator modelValidator) {
		return new UsStatesModelBuilder(modelValidator);
  	}
}
