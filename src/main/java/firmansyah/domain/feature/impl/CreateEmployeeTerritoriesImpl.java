// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateEmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.*;
import firmansyah.domain.exception.EmployeeTerritoriesAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateEmployeeTerritoriesImpl implements CreateEmployeeTerritories {

	private final EmployeeTerritoriesRepository employeeTerritoriesRepository;
	private final EmployeeTerritoriesModelBuilder employeeTerritoriesBuilder;
	private final FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey;
	private final FindTerritoriesByPrimaryKey findTerritoriesTerritoryIdByPrimaryKey;
	

	@Override
	public EmployeeTerritories handle(NewEmployeeTerritoriesInput newEmployeeTerritoriesInput) {
		final var employeeTerritories =
			employeeTerritoriesBuilder.build(findEmployeesEmployeeIdByPrimaryKey.handle(newEmployeeTerritoriesInput.getEmployeeId()),
					findTerritoriesTerritoryIdByPrimaryKey.handle(newEmployeeTerritoriesInput.getTerritoryId()));
		
		if(employeeTerritoriesRepository.findEmployeeTerritoriesByPrimaryKey(employeeTerritories.getEmployeesEmployeeId().getEmployeeId(), employeeTerritories.getTerritoriesTerritoryId().getTerritoryId()).isPresent()) {
			throw new EmployeeTerritoriesAlreadyExistsException();
		} else {
			employeeTerritoriesRepository.save(employeeTerritories);
		}
   
		return employeeTerritories;
	}
}
