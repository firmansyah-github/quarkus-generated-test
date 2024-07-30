// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.customers.CustomersModelBuilder;
import firmansyah.domain.model.customers.CustomersRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class CustomersConfiguration {

	@Produces
  	@Singleton
  	public CreateCustomers createCustomers(
		CustomersRepository customersRepository,
      	CustomersModelBuilder customersBuilder ) {
    	return new CreateCustomersImpl(
        	customersRepository,
        	customersBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteCustomers deleteCustomers(
		CustomersRepository customersRepository) {
    		return new DeleteCustomersImpl(
        			customersRepository);
  	}

  	@Produces
  	@Singleton
  	public FindCustomersByFilter findCustomersByFilter(CustomersRepository customersRepository) {
    	return new FindCustomersByFilterImpl(customersRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindCustomersByPrimaryKey findCustomersByPrimaryKey(CustomersRepository customersRepository) {
		return new FindCustomersByPrimaryKeyImpl(customersRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateCustomers updateCustomers(
		CustomersRepository customersRepository,
      	CustomersModelBuilder customersBuilder,
      	FindCustomersByPrimaryKey findCustomersByPrimaryKey	) {
		return new UpdateCustomersImpl(
        	customersRepository,
        	customersBuilder,
        	findCustomersByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public CustomersModelBuilder customersBuilder(ModelValidator modelValidator) {
		return new CustomersModelBuilder(modelValidator);
  	}
}
