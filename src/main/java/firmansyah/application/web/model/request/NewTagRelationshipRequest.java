// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.tagRelationship.NewTagRelationshipInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("tagRelationship")
@RegisterForReflection
public class NewTagRelationshipRequest {
  
	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MAX_LENGTH, max = 255)
	private String tagId;
  

	public NewTagRelationshipInput toNewTagRelationshipInput() {
		return new NewTagRelationshipInput(
			this.articleId, this.tagId
		);
  	}

}
