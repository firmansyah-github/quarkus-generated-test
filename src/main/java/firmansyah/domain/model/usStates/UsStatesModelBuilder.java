// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.usStates;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import java.util.UUID;

@AllArgsConstructor
public class UsStatesModelBuilder {

	private final ModelValidator modelValidator;

	public UsStates build(Integer stateId, String stateName, String stateAbbr, String stateRegion) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new UsStates(stateId, stateName, stateAbbr, stateRegion));
	}
  
}
