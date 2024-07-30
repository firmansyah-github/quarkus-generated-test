// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employeeTerritories;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;



import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.model.territories.Territories;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeTerritories {
	
	private Employees employeesEmployeeId;
	private Territories territoriesTerritoryId;
	
}
