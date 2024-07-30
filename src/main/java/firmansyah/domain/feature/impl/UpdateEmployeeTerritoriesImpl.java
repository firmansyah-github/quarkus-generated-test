// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.employeeTerritories.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateEmployeeTerritoriesImpl implements UpdateEmployeeTerritories {

	private final EmployeeTerritoriesRepository employeeTerritoriesRepository;
	private final EmployeeTerritoriesModelBuilder employeeTerritoriesBuilder;
	private final FindEmployeeTerritoriesByPrimaryKey findEmployeeTerritoriesByPrimaryKey;
	private final FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey;
	private final FindTerritoriesByPrimaryKey findTerritoriesTerritoryIdByPrimaryKey;
	

	@Override
	public EmployeeTerritories handle(UpdateEmployeeTerritoriesInput updateEmployeeTerritoriesInput) {
		var employeeTerritories = findEmployeeTerritoriesByPrimaryKey.handle(updateEmployeeTerritoriesInput.getEmployeeId(), updateEmployeeTerritoriesInput.getTerritoryId());
		
    	employeeTerritories =
			employeeTerritoriesBuilder.build(findEmployeesEmployeeIdByPrimaryKey.handle(updateEmployeeTerritoriesInput.getEmployeeId()),
					findTerritoriesTerritoryIdByPrimaryKey.handle(updateEmployeeTerritoriesInput.getTerritoryId()));
		employeeTerritoriesRepository.update(employeeTerritories);
    
		return employeeTerritories;
	}
}
