// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindEmployeeTerritoriesByFilter {
	PageResult<EmployeeTerritories> handle(ResourceFilter resourceFilter);
}