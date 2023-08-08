package org.example.realworldapi.domain.model.tagRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewTagRelationshipInput {
	private String tagId;
	private String articleId;
}
