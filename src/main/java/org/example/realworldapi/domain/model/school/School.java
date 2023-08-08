package org.example.realworldapi.domain.model.school;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class School {
	@NotBlank(message = ValidationMessages.SCHOOL_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SCHOOL_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.SCHOOL_NAME_MAX_LENGTH, max = 255)
	private String name;
	
	
}
