// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.territories.TerritoriesModelBuilder;
import firmansyah.domain.model.territories.TerritoriesRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class TerritoriesConfiguration {

	@Produces
  	@Singleton
  	public CreateTerritories createTerritories(
		TerritoriesRepository territoriesRepository,
      	TerritoriesModelBuilder territoriesBuilder ,FindRegionByPrimaryKey findRegionRegionIdByPrimaryKey) {
    	return new CreateTerritoriesImpl(
        	territoriesRepository,
        	territoriesBuilder ,findRegionRegionIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteTerritories deleteTerritories(
		TerritoriesRepository territoriesRepository) {
    		return new DeleteTerritoriesImpl(
        			territoriesRepository);
  	}

  	@Produces
  	@Singleton
  	public FindTerritoriesByFilter findTerritoriesByFilter(TerritoriesRepository territoriesRepository) {
    	return new FindTerritoriesByFilterImpl(territoriesRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindTerritoriesByPrimaryKey findTerritoriesByPrimaryKey(TerritoriesRepository territoriesRepository) {
		return new FindTerritoriesByPrimaryKeyImpl(territoriesRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateTerritories updateTerritories(
		TerritoriesRepository territoriesRepository,
      	TerritoriesModelBuilder territoriesBuilder,
      	FindTerritoriesByPrimaryKey findTerritoriesByPrimaryKey	,FindRegionByPrimaryKey findRegionRegionIdByPrimaryKey) {
		return new UpdateTerritoriesImpl(
        	territoriesRepository,
        	territoriesBuilder,
        	findTerritoriesByPrimaryKey ,findRegionRegionIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public TerritoriesModelBuilder territoriesBuilder(ModelValidator modelValidator) {
		return new TerritoriesModelBuilder(modelValidator);
  	}
}
