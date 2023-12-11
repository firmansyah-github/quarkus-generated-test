// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.model.tags;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.tagRelationship.TagRelationship;
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
