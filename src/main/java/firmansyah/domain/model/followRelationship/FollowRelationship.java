// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.followRelationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.Users;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRelationship {
	
	private Users usersFollowedId;
	private Users usersUserId;
	
}
