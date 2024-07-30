// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindEmployeeTerritoriesByFilter;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindEmployeeTerritoriesByFilterImpl implements FindEmployeeTerritoriesByFilter {

	private final EmployeeTerritoriesRepository employeeTerritoriesRepository;

	@Override
	public PageResult<EmployeeTerritories> handle(ResourceFilter resourceFilterr) {
		return employeeTerritoriesRepository.findEmployeeTerritoriesByFilter(resourceFilterr);
	}
}
