// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
