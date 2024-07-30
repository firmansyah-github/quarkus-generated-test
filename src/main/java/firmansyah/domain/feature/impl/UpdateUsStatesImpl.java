// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.usStates.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateUsStatesImpl implements UpdateUsStates {

	private final UsStatesRepository usStatesRepository;
	private final UsStatesModelBuilder usStatesBuilder;
	private final FindUsStatesByPrimaryKey findUsStatesByPrimaryKey;
	

	@Override
	public UsStates handle(UpdateUsStatesInput updateUsStatesInput) {
		var usStates = findUsStatesByPrimaryKey.handle(updateUsStatesInput.getStateId());
		
    	usStates =
			usStatesBuilder.build(updateUsStatesInput.getStateId(),
					updateUsStatesInput.getStateName(),
					updateUsStatesInput.getStateAbbr(),
					updateUsStatesInput.getStateRegion());
		usStatesRepository.update(usStates);
    
		return usStates;
	}
}
