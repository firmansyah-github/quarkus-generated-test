package org.example.realworldapi.domain.model.tagRelationship;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            



import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.domain.model.articles.Articles;
import java.util.UUID;

@AllArgsConstructor
public class TagRelationshipModelBuilder {

	private final ModelValidator modelValidator;

	public TagRelationship build(Tags tagsTagId, Articles articlesArticleId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new TagRelationship(tagsTagId, articlesArticleId));
	}
  
}
