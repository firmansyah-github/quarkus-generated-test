// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.followRelationship.NewFollowRelationshipInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("followRelationship")
@RegisterForReflection
public class NewFollowRelationshipRequest {
  
	@NotBlank(message = ValidationMessages.FOLLOWRELATIONSHIP_FOLLOWEDID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FOLLOWRELATIONSHIP_FOLLOWEDID_MAX_LENGTH, max = 255)
	private String followedId;
	@NotBlank(message = ValidationMessages.FOLLOWRELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FOLLOWRELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;
  

	public NewFollowRelationshipInput toNewFollowRelationshipInput() {
		return new NewFollowRelationshipInput(
			this.followedId, this.userId
		);
  	}

}
