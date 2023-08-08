package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




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
