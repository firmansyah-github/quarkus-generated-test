// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
            






public interface FindEmployeeTerritoriesByPrimaryKey {
	EmployeeTerritories handle(Integer employeeId, String territoryId);
}

