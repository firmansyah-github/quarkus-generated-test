// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateUsStates;
import firmansyah.domain.model.usStates.*;
import firmansyah.domain.exception.UsStatesAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateUsStatesImpl implements CreateUsStates {

	private final UsStatesRepository usStatesRepository;
	private final UsStatesModelBuilder usStatesBuilder;
	

	@Override
	public UsStates handle(NewUsStatesInput newUsStatesInput) {
		final var usStates =
			usStatesBuilder.build(newUsStatesInput.getStateId(),
					newUsStatesInput.getStateName(),
					newUsStatesInput.getStateAbbr(),
					newUsStatesInput.getStateRegion());
		
		if(usStatesRepository.findUsStatesByPrimaryKey(usStates.getStateId()).isPresent()) {
			throw new UsStatesAlreadyExistsException();
		} else {
			usStatesRepository.save(usStates);
		}
   
		return usStates;
	}
}
