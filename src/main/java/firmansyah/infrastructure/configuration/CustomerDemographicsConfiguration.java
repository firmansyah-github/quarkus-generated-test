// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsModelBuilder;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class CustomerDemographicsConfiguration {

	@Produces
  	@Singleton
  	public CreateCustomerDemographics createCustomerDemographics(
		CustomerDemographicsRepository customerDemographicsRepository,
      	CustomerDemographicsModelBuilder customerDemographicsBuilder ) {
    	return new CreateCustomerDemographicsImpl(
        	customerDemographicsRepository,
        	customerDemographicsBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteCustomerDemographics deleteCustomerDemographics(
		CustomerDemographicsRepository customerDemographicsRepository) {
    		return new DeleteCustomerDemographicsImpl(
        			customerDemographicsRepository);
  	}

  	@Produces
  	@Singleton
  	public FindCustomerDemographicsByFilter findCustomerDemographicsByFilter(CustomerDemographicsRepository customerDemographicsRepository) {
    	return new FindCustomerDemographicsByFilterImpl(customerDemographicsRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindCustomerDemographicsByPrimaryKey findCustomerDemographicsByPrimaryKey(CustomerDemographicsRepository customerDemographicsRepository) {
		return new FindCustomerDemographicsByPrimaryKeyImpl(customerDemographicsRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateCustomerDemographics updateCustomerDemographics(
		CustomerDemographicsRepository customerDemographicsRepository,
      	CustomerDemographicsModelBuilder customerDemographicsBuilder,
      	FindCustomerDemographicsByPrimaryKey findCustomerDemographicsByPrimaryKey	) {
		return new UpdateCustomerDemographicsImpl(
        	customerDemographicsRepository,
        	customerDemographicsBuilder,
        	findCustomerDemographicsByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public CustomerDemographicsModelBuilder customerDemographicsBuilder(ModelValidator modelValidator) {
		return new CustomerDemographicsModelBuilder(modelValidator);
  	}
}
