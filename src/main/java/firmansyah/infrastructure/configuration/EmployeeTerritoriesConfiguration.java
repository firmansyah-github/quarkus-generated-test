// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesModelBuilder;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class EmployeeTerritoriesConfiguration {

	@Produces
  	@Singleton
  	public CreateEmployeeTerritories createEmployeeTerritories(
		EmployeeTerritoriesRepository employeeTerritoriesRepository,
      	EmployeeTerritoriesModelBuilder employeeTerritoriesBuilder ,FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey,FindTerritoriesByPrimaryKey findTerritoriesTerritoryIdByPrimaryKey) {
    	return new CreateEmployeeTerritoriesImpl(
        	employeeTerritoriesRepository,
        	employeeTerritoriesBuilder ,findEmployeesEmployeeIdByPrimaryKey,findTerritoriesTerritoryIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteEmployeeTerritories deleteEmployeeTerritories(
		EmployeeTerritoriesRepository employeeTerritoriesRepository) {
    		return new DeleteEmployeeTerritoriesImpl(
        			employeeTerritoriesRepository);
  	}

  	@Produces
  	@Singleton
  	public FindEmployeeTerritoriesByFilter findEmployeeTerritoriesByFilter(EmployeeTerritoriesRepository employeeTerritoriesRepository) {
    	return new FindEmployeeTerritoriesByFilterImpl(employeeTerritoriesRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindEmployeeTerritoriesByPrimaryKey findEmployeeTerritoriesByPrimaryKey(EmployeeTerritoriesRepository employeeTerritoriesRepository) {
		return new FindEmployeeTerritoriesByPrimaryKeyImpl(employeeTerritoriesRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateEmployeeTerritories updateEmployeeTerritories(
		EmployeeTerritoriesRepository employeeTerritoriesRepository,
      	EmployeeTerritoriesModelBuilder employeeTerritoriesBuilder,
      	FindEmployeeTerritoriesByPrimaryKey findEmployeeTerritoriesByPrimaryKey	,FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey,FindTerritoriesByPrimaryKey findTerritoriesTerritoryIdByPrimaryKey) {
		return new UpdateEmployeeTerritoriesImpl(
        	employeeTerritoriesRepository,
        	employeeTerritoriesBuilder,
        	findEmployeeTerritoriesByPrimaryKey ,findEmployeesEmployeeIdByPrimaryKey,findTerritoriesTerritoryIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public EmployeeTerritoriesModelBuilder employeeTerritoriesBuilder(ModelValidator modelValidator) {
		return new EmployeeTerritoriesModelBuilder(modelValidator);
  	}
}
