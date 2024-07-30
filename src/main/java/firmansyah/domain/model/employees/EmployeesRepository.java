// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employees;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface EmployeesRepository {

	void save(Employees employees);

	Optional<Employees> findEmployeesByPrimaryKey(Integer employeeId);

	void update(Employees employees);

	boolean delete(Integer employeeId);

    PageResult<Employees> findEmployeesByFilter(ResourceFilter resourceFilter);
    
	long countEmployees();
}
