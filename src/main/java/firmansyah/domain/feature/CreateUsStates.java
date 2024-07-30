// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.usStates.UsStates;
import firmansyah.domain.model.usStates.NewUsStatesInput;

public interface CreateUsStates {
	UsStates handle(NewUsStatesInput newUsStatesInput);
}
