// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.NewEmployeeTerritoriesInput;

public interface CreateEmployeeTerritories {
	EmployeeTerritories handle(NewEmployeeTerritoriesInput newEmployeeTerritoriesInput);
}
