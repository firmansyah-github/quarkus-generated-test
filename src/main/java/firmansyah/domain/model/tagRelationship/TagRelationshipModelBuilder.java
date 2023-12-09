// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.tagRelationship;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.articles.Articles;
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
