// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoModelBuilder;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class CustomerCustomerDemoConfiguration {

	@Produces
  	@Singleton
  	public CreateCustomerCustomerDemo createCustomerCustomerDemo(
		CustomerCustomerDemoRepository customerCustomerDemoRepository,
      	CustomerCustomerDemoModelBuilder customerCustomerDemoBuilder ,FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey,FindCustomerDemographicsByPrimaryKey findCustomerDemographicsCustomerTypeIdByPrimaryKey) {
    	return new CreateCustomerCustomerDemoImpl(
        	customerCustomerDemoRepository,
        	customerCustomerDemoBuilder ,findCustomersCustomerIdByPrimaryKey,findCustomerDemographicsCustomerTypeIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteCustomerCustomerDemo deleteCustomerCustomerDemo(
		CustomerCustomerDemoRepository customerCustomerDemoRepository) {
    		return new DeleteCustomerCustomerDemoImpl(
        			customerCustomerDemoRepository);
  	}

  	@Produces
  	@Singleton
  	public FindCustomerCustomerDemoByFilter findCustomerCustomerDemoByFilter(CustomerCustomerDemoRepository customerCustomerDemoRepository) {
    	return new FindCustomerCustomerDemoByFilterImpl(customerCustomerDemoRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindCustomerCustomerDemoByPrimaryKey findCustomerCustomerDemoByPrimaryKey(CustomerCustomerDemoRepository customerCustomerDemoRepository) {
		return new FindCustomerCustomerDemoByPrimaryKeyImpl(customerCustomerDemoRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateCustomerCustomerDemo updateCustomerCustomerDemo(
		CustomerCustomerDemoRepository customerCustomerDemoRepository,
      	CustomerCustomerDemoModelBuilder customerCustomerDemoBuilder,
      	FindCustomerCustomerDemoByPrimaryKey findCustomerCustomerDemoByPrimaryKey	,FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey,FindCustomerDemographicsByPrimaryKey findCustomerDemographicsCustomerTypeIdByPrimaryKey) {
		return new UpdateCustomerCustomerDemoImpl(
        	customerCustomerDemoRepository,
        	customerCustomerDemoBuilder,
        	findCustomerCustomerDemoByPrimaryKey ,findCustomersCustomerIdByPrimaryKey,findCustomerDemographicsCustomerTypeIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public CustomerCustomerDemoModelBuilder customerCustomerDemoBuilder(ModelValidator modelValidator) {
		return new CustomerCustomerDemoModelBuilder(modelValidator);
  	}
}
