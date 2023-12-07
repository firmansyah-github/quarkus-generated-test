// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.followRelationship.UpdateFollowRelationshipInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("followRelationship")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateFollowRelationshipRequest {

	@NotBlank(message = ValidationMessages.FOLLOWRELATIONSHIP_FOLLOWEDID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FOLLOWRELATIONSHIP_FOLLOWEDID_MAX_LENGTH, max = 255)
	private String followedId;
	@NotBlank(message = ValidationMessages.FOLLOWRELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FOLLOWRELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;

	public UpdateFollowRelationshipInput toUpdateFollowRelationshipInput() {
		return new UpdateFollowRelationshipInput(
    		this.followedId, this.userId
		);
  }
}
