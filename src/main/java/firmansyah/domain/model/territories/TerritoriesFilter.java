// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.territories;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class TerritoriesFilter {
	private final int offset;
	private final int limit;
	private String territoryId;
	private String territoryDescription;
	private Integer regionId;
}
