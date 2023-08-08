package org.example.realworldapi.domain.model.followRelationship;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            



import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.users.Users;
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
