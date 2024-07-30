// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.usStates;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class UsStatesFilter {
	private final int offset;
	private final int limit;
	private Integer stateId;
	private String stateName;
	private String stateAbbr;
	private String stateRegion;
}
