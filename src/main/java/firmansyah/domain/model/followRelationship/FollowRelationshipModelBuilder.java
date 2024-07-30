// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.followRelationship;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.Users;
import java.util.UUID;

@AllArgsConstructor
public class FollowRelationshipModelBuilder {

	private final ModelValidator modelValidator;

	public FollowRelationship build(Users usersFollowedId, Users usersUserId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new FollowRelationship(usersFollowedId, usersUserId));
	}
  
}
