// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.shippers.ShippersModelBuilder;
import firmansyah.domain.model.shippers.ShippersRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class ShippersConfiguration {

	@Produces
  	@Singleton
  	public CreateShippers createShippers(
		ShippersRepository shippersRepository,
      	ShippersModelBuilder shippersBuilder ) {
    	return new CreateShippersImpl(
        	shippersRepository,
        	shippersBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteShippers deleteShippers(
		ShippersRepository shippersRepository) {
    		return new DeleteShippersImpl(
        			shippersRepository);
  	}

  	@Produces
  	@Singleton
  	public FindShippersByFilter findShippersByFilter(ShippersRepository shippersRepository) {
    	return new FindShippersByFilterImpl(shippersRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindShippersByPrimaryKey findShippersByPrimaryKey(ShippersRepository shippersRepository) {
		return new FindShippersByPrimaryKeyImpl(shippersRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateShippers updateShippers(
		ShippersRepository shippersRepository,
      	ShippersModelBuilder shippersBuilder,
      	FindShippersByPrimaryKey findShippersByPrimaryKey	) {
		return new UpdateShippersImpl(
        	shippersRepository,
        	shippersBuilder,
        	findShippersByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public ShippersModelBuilder shippersBuilder(ModelValidator modelValidator) {
		return new ShippersModelBuilder(modelValidator);
  	}
}
