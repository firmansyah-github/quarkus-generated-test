// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.UpdateEmployeeTerritoriesInput;


public interface UpdateEmployeeTerritories {
	EmployeeTerritories handle(UpdateEmployeeTerritoriesInput updateEmployeeTerritoriesInput);
}
