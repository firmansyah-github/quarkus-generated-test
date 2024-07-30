// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employeeTerritories;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class EmployeeTerritoriesFilter {
	private final int offset;
	private final int limit;
	private Integer employeeId;
	private String territoryId;
}
