// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.employees.NewEmployeesInput;

public interface CreateEmployees {
	Employees handle(NewEmployeesInput newEmployeesInput);
}
