package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tagRelationship.UpdateTagRelationshipInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("tagRelationship")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateTagRelationshipRequest {

	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MAX_LENGTH, max = 255)
	private String tagId;
	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;

	public UpdateTagRelationshipInput toUpdateTagRelationshipInput() {
		return new UpdateTagRelationshipInput(
    		this.tagId, this.articleId
		);
  }
}
