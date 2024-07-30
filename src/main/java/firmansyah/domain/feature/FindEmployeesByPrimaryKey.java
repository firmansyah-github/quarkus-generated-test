// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employees.Employees;
            

import java.time.LocalDateTime;





public interface FindEmployeesByPrimaryKey {
	Employees handle(Integer employeeId);
}

