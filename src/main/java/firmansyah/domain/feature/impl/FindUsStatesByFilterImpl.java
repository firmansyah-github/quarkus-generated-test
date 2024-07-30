// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindUsStatesByFilter;
import firmansyah.domain.model.usStates.UsStates;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.usStates.UsStatesRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindUsStatesByFilterImpl implements FindUsStatesByFilter {

	private final UsStatesRepository usStatesRepository;

	@Override
	public PageResult<UsStates> handle(ResourceFilter resourceFilterr) {
		return usStatesRepository.findUsStatesByFilter(resourceFilterr);
	}
}
