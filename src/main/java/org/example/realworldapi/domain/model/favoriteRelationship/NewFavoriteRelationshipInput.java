package org.example.realworldapi.domain.model.favoriteRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewFavoriteRelationshipInput {
	private String userId;
	private String articleId;
}
