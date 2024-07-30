// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.usStates;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewUsStatesInput {
	private Integer stateId;
	private String stateName;
	private String stateAbbr;
	private String stateRegion;
}
