// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employees.Employees;
import java.util.List;



public interface FindEmployeesByForeignKey {
  
	List<Employees> handleForReportsTo(java.lang.Integer ReportsTo);
}

