package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.tagRelationship.NewTagRelationshipInput;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("tagRelationship")
@RegisterForReflection
public class NewTagRelationshipRequest {
  
	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_TAGID_MAX_LENGTH, max = 255)
	private String tagId;
	@NotBlank(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGRELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
  

	public NewTagRelationshipInput toNewTagRelationshipInput() {
		return new NewTagRelationshipInput(
			this.tagId, this.articleId
		);
  	}

}
