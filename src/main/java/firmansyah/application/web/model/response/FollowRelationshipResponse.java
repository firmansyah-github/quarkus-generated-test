// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
