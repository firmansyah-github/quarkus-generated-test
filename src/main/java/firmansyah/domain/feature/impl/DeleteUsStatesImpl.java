// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteUsStates;
import firmansyah.domain.model.usStates.UsStatesRepository;


@AllArgsConstructor
public class DeleteUsStatesImpl implements DeleteUsStates {

	private final UsStatesRepository usStatesRepository;

  	@Override
	public boolean handle(Integer stateId) {
		return usStatesRepository.delete(stateId);
	}
}
