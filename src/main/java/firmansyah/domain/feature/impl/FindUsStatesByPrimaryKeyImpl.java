// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindUsStatesByPrimaryKey;
import firmansyah.domain.exception.UsStatesNotFoundException;
import firmansyah.domain.model.usStates.UsStates;
import firmansyah.domain.model.usStates.UsStatesRepository;


@AllArgsConstructor
public class FindUsStatesByPrimaryKeyImpl implements FindUsStatesByPrimaryKey {

	private final UsStatesRepository usStatesRepository;

	@Override
	public UsStates handle(Integer stateId) {
		return usStatesRepository.findUsStatesByPrimaryKey(stateId)
    			.orElseThrow(UsStatesNotFoundException::new);
	}
}
