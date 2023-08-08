package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.followRelationship.UpdateFollowRelationshipInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("followRelationship")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateFollowRelationshipRequest {

	@NotBlank(message = ValidationMessages.FOLLOWRELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FOLLOWRELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;
	@NotBlank(message = ValidationMessages.FOLLOWRELATIONSHIP_FOLLOWEDID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FOLLOWRELATIONSHIP_FOLLOWEDID_MAX_LENGTH, max = 255)
	private String followedId;

	public UpdateFollowRelationshipInput toUpdateFollowRelationshipInput() {
		return new UpdateFollowRelationshipInput(
    		this.userId, this.followedId
		);
  }
}
