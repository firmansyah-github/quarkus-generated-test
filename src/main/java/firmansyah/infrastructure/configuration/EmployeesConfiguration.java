// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.employees.EmployeesModelBuilder;
import firmansyah.domain.model.employees.EmployeesRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class EmployeesConfiguration {

	@Produces
  	@Singleton
  	public CreateEmployees createEmployees(
		EmployeesRepository employeesRepository,
      	EmployeesModelBuilder employeesBuilder ,FindEmployeesByPrimaryKey findEmployeesReportsToByPrimaryKey) {
    	return new CreateEmployeesImpl(
        	employeesRepository,
        	employeesBuilder ,findEmployeesReportsToByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteEmployees deleteEmployees(
		EmployeesRepository employeesRepository) {
    		return new DeleteEmployeesImpl(
        			employeesRepository);
  	}

  	@Produces
  	@Singleton
  	public FindEmployeesByFilter findEmployeesByFilter(EmployeesRepository employeesRepository) {
    	return new FindEmployeesByFilterImpl(employeesRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindEmployeesByPrimaryKey findEmployeesByPrimaryKey(EmployeesRepository employeesRepository) {
		return new FindEmployeesByPrimaryKeyImpl(employeesRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateEmployees updateEmployees(
		EmployeesRepository employeesRepository,
      	EmployeesModelBuilder employeesBuilder,
      	FindEmployeesByPrimaryKey findEmployeesByPrimaryKey	,FindEmployeesByPrimaryKey findEmployeesReportsToByPrimaryKey) {
		return new UpdateEmployeesImpl(
        	employeesRepository,
        	employeesBuilder,
        	findEmployeesByPrimaryKey ,findEmployeesReportsToByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public EmployeesModelBuilder employeesBuilder(ModelValidator modelValidator) {
		return new EmployeesModelBuilder(modelValidator);
  	}
}
