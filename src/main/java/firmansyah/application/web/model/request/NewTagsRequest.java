// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.tags.NewTagsInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("tags")
@RegisterForReflection
public class NewTagsRequest {
  
	@NotBlank(message = ValidationMessages.TAGS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.TAGS_NAME_MAX_LENGTH, max = 255)
	private String name;
  

	public NewTagsInput toNewTagsInput() {
		return new NewTagsInput(
			this.id, this.name
		);
  	}

}
