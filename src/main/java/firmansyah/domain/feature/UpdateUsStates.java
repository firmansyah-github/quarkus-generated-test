// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.usStates.UsStates;
import firmansyah.domain.model.usStates.UpdateUsStatesInput;


public interface UpdateUsStates {
	UsStates handle(UpdateUsStatesInput updateUsStatesInput);
}
