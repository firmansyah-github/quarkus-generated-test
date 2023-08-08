package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tags.Tags;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("tags")
@RegisterForReflection
public class TagsResponse {

	@NotBlank(message = ValidationMessages.TAGS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.TAGS_NAME_MAX_LENGTH, max = 255)
	private String name;
	

	public TagsResponse(Tags tags) {
								
		this.id = tags.getId();
		this.name = tags.getName();
		

	}
    
	public TagsResponse(boolean isFlag, Tags tags) {
		if(isFlag){
			this.id = tags.getId();
			this.name = tags.getName();
			
		}
	}
  
}
