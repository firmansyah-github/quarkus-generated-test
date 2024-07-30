// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.suppliers.SuppliersModelBuilder;
import firmansyah.domain.model.suppliers.SuppliersRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class SuppliersConfiguration {

	@Produces
  	@Singleton
  	public CreateSuppliers createSuppliers(
		SuppliersRepository suppliersRepository,
      	SuppliersModelBuilder suppliersBuilder ) {
    	return new CreateSuppliersImpl(
        	suppliersRepository,
        	suppliersBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteSuppliers deleteSuppliers(
		SuppliersRepository suppliersRepository) {
    		return new DeleteSuppliersImpl(
        			suppliersRepository);
  	}

  	@Produces
  	@Singleton
  	public FindSuppliersByFilter findSuppliersByFilter(SuppliersRepository suppliersRepository) {
    	return new FindSuppliersByFilterImpl(suppliersRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindSuppliersByPrimaryKey findSuppliersByPrimaryKey(SuppliersRepository suppliersRepository) {
		return new FindSuppliersByPrimaryKeyImpl(suppliersRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateSuppliers updateSuppliers(
		SuppliersRepository suppliersRepository,
      	SuppliersModelBuilder suppliersBuilder,
      	FindSuppliersByPrimaryKey findSuppliersByPrimaryKey	) {
		return new UpdateSuppliersImpl(
        	suppliersRepository,
        	suppliersBuilder,
        	findSuppliersByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public SuppliersModelBuilder suppliersBuilder(ModelValidator modelValidator) {
		return new SuppliersModelBuilder(modelValidator);
  	}
}
