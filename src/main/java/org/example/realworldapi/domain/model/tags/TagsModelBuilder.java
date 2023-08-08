package org.example.realworldapi.domain.model.tags;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            


import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class TagsModelBuilder {

	private final ModelValidator modelValidator;

	public Tags build(String id, String name, List<TagRelationship> tagRelationshipTagIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Tags(id, name, tagRelationshipTagIdList));
	}
  
}
