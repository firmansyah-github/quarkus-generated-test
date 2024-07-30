// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employeeTerritories;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface EmployeeTerritoriesRepository {

	void save(EmployeeTerritories employeeTerritories);

	Optional<EmployeeTerritories> findEmployeeTerritoriesByPrimaryKey(Integer employeeId,String territoryId);

	void update(EmployeeTerritories employeeTerritories);

	boolean delete(Integer employeeId,String territoryId);

    PageResult<EmployeeTerritories> findEmployeeTerritoriesByFilter(ResourceFilter resourceFilter);
    
	long countEmployeeTerritories();
}
