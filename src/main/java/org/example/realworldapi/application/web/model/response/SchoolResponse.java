package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.school.School;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("school")
@RegisterForReflection
public class SchoolResponse {

	@NotBlank(message = ValidationMessages.SCHOOL_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SCHOOL_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.SCHOOL_NAME_MAX_LENGTH, max = 255)
	private String name;
	

	public SchoolResponse(School school) {
								
		this.id = school.getId();
		this.name = school.getName();
		

	}
    
	public SchoolResponse(boolean isFlag, School school) {
		if(isFlag){
			this.id = school.getId();
			this.name = school.getName();
			
		}
	}
  
}
