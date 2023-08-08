package org.example.realworldapi.domain.model.followRelationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.users.Users;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRelationship {
	
	private Users usersFollowedId;
	private Users usersUserId;
	
}
