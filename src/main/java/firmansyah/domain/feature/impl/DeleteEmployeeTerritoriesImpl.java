// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteEmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesRepository;


@AllArgsConstructor
public class DeleteEmployeeTerritoriesImpl implements DeleteEmployeeTerritories {

	private final EmployeeTerritoriesRepository employeeTerritoriesRepository;

  	@Override
	public boolean handle(Integer employeeId, String territoryId) {
		return employeeTerritoriesRepository.delete(employeeId, territoryId);
	}
}
