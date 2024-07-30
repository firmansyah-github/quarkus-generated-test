// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindEmployeeTerritoriesByPrimaryKey;
import firmansyah.domain.exception.EmployeeTerritoriesNotFoundException;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesRepository;


@AllArgsConstructor
public class FindEmployeeTerritoriesByPrimaryKeyImpl implements FindEmployeeTerritoriesByPrimaryKey {

	private final EmployeeTerritoriesRepository employeeTerritoriesRepository;

	@Override
	public EmployeeTerritories handle(Integer employeeId, String territoryId) {
		return employeeTerritoriesRepository.findEmployeeTerritoriesByPrimaryKey(employeeId, territoryId)
    			.orElseThrow(EmployeeTerritoriesNotFoundException::new);
	}
}
