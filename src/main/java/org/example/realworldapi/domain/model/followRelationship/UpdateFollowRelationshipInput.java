package org.example.realworldapi.domain.model.followRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateFollowRelationshipInput {
	private String userId;
	private String followedId;
}
