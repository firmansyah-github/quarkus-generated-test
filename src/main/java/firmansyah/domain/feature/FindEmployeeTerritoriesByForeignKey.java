// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import java.util.List;



public interface FindEmployeeTerritoriesByForeignKey {
  
	List<EmployeeTerritories> handleForEmployeeId(java.lang.Integer EmployeeId);
	List<EmployeeTerritories> handleForTerritoryId(java.lang.String TerritoryId);
}

