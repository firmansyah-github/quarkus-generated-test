// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.region.RegionModelBuilder;
import firmansyah.domain.model.region.RegionRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class RegionConfiguration {

	@Produces
  	@Singleton
  	public CreateRegion createRegion(
		RegionRepository regionRepository,
      	RegionModelBuilder regionBuilder ) {
    	return new CreateRegionImpl(
        	regionRepository,
        	regionBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteRegion deleteRegion(
		RegionRepository regionRepository) {
    		return new DeleteRegionImpl(
        			regionRepository);
  	}

  	@Produces
  	@Singleton
  	public FindRegionByFilter findRegionByFilter(RegionRepository regionRepository) {
    	return new FindRegionByFilterImpl(regionRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindRegionByPrimaryKey findRegionByPrimaryKey(RegionRepository regionRepository) {
		return new FindRegionByPrimaryKeyImpl(regionRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateRegion updateRegion(
		RegionRepository regionRepository,
      	RegionModelBuilder regionBuilder,
      	FindRegionByPrimaryKey findRegionByPrimaryKey	) {
		return new UpdateRegionImpl(
        	regionRepository,
        	regionBuilder,
        	findRegionByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public RegionModelBuilder regionBuilder(ModelValidator modelValidator) {
		return new RegionModelBuilder(modelValidator);
  	}
}
