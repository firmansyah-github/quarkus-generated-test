// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.territories;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateTerritoriesInput {
	private String territoryId;
	private String territoryDescription;
	private Integer regionId;
}
