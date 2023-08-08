package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.school.NewSchoolInput;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("school")
@RegisterForReflection
public class NewSchoolRequest {
  
	@NotBlank(message = ValidationMessages.SCHOOL_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SCHOOL_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.SCHOOL_NAME_MAX_LENGTH, max = 255)
	private String name;
  

	public NewSchoolInput toNewSchoolInput() {
		return new NewSchoolInput(
			this.id, this.name
		);
  	}

}
