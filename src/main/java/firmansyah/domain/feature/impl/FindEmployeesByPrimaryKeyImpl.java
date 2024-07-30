// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindEmployeesByPrimaryKey;
import firmansyah.domain.exception.EmployeesNotFoundException;
import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.employees.EmployeesRepository;


@AllArgsConstructor
public class FindEmployeesByPrimaryKeyImpl implements FindEmployeesByPrimaryKey {

	private final EmployeesRepository employeesRepository;

	@Override
	public Employees handle(Integer employeeId) {
		return employeesRepository.findEmployeesByPrimaryKey(employeeId)
    			.orElseThrow(EmployeesNotFoundException::new);
	}
}
