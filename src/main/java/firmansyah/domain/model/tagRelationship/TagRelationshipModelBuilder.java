// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.tagRelationship;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.tags.Tags;
import java.util.UUID;

@AllArgsConstructor
public class TagRelationshipModelBuilder {

	private final ModelValidator modelValidator;

	public TagRelationship build(Articles articlesArticleId, Tags tagsTagId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new TagRelationship(articlesArticleId, tagsTagId));
	}
  
}
