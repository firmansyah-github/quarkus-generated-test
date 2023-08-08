package org.example.realworldapi.domain.model.school;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            



import java.util.UUID;

@AllArgsConstructor
public class SchoolModelBuilder {

	private final ModelValidator modelValidator;

	public School build(String id, String name) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new School(id, name));
	}
  
}
