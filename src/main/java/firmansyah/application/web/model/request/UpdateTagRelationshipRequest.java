// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.tagRelationship.UpdateTagRelationshipInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("tagRelationship")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateTagRelationshipRequest {

	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MAX_LENGTH, max = 255)
	private String tagId;

	public UpdateTagRelationshipInput toUpdateTagRelationshipInput() {
		return new UpdateTagRelationshipInput(
    		this.articleId, this.tagId
		);
  }
}
