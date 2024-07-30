// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindEmployeesByFilter;
import firmansyah.domain.model.employees.Employees;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.employees.EmployeesRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindEmployeesByFilterImpl implements FindEmployeesByFilter {

	private final EmployeesRepository employeesRepository;

	@Override
	public PageResult<Employees> handle(ResourceFilter resourceFilterr) {
		return employeesRepository.findEmployeesByFilter(resourceFilterr);
	}
}
