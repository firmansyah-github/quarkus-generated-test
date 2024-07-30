// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employeeTerritories;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.territories.Territories;
import java.util.UUID;

@AllArgsConstructor
public class EmployeeTerritoriesModelBuilder {

	private final ModelValidator modelValidator;

	public EmployeeTerritories build(Employees employeesEmployeeId, Territories territoriesTerritoryId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new EmployeeTerritories(employeesEmployeeId, territoriesTerritoryId));
	}
  
}
