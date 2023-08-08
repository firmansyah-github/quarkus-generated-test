package org.example.realworldapi.domain.model.tags;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tags {
	@NotBlank(message = ValidationMessages.TAGS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.TAGS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.TAGS_NAME_MAX_LENGTH, max = 255)
	private String name;
	private List<TagRelationship> tagRelationshipTagIdList;
	
	
}
