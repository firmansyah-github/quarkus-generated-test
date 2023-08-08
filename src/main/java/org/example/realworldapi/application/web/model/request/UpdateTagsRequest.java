package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tags.UpdateTagsInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("tags")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateTagsRequest {

	@NotBlank(message = ValidationMessages.TAGS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.TAGS_NAME_MAX_LENGTH, max = 255)
	private String name;

	public UpdateTagsInput toUpdateTagsInput() {
		return new UpdateTagsInput(
    		this.id, this.name
		);
  }
}
