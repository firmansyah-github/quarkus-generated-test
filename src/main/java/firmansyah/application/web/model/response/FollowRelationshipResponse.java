// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.followRelationship.FollowRelationship;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("followRelationship")
@RegisterForReflection
public class FollowRelationshipResponse {

	private UsersResponse usersFollowedIdResponse;
	private UsersResponse usersUserIdResponse;
	

	public FollowRelationshipResponse(FollowRelationship followRelationship,  UsersResponse usersFollowedIdResponse,  UsersResponse usersUserIdResponse) {
								
		this.usersFollowedIdResponse =usersFollowedIdResponse;
		this.usersUserIdResponse =usersUserIdResponse;
		

	}
    
  
}
