// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteEmployees;
import firmansyah.domain.model.employees.EmployeesRepository;


@AllArgsConstructor
public class DeleteEmployeesImpl implements DeleteEmployees {

	private final EmployeesRepository employeesRepository;

  	@Override
	public boolean handle(Integer employeeId) {
		return employeesRepository.delete(employeeId);
	}
}
