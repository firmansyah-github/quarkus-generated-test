// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.model.favoriteRelationship;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            



import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.articles.Articles;
import java.util.UUID;

@AllArgsConstructor
public class FavoriteRelationshipModelBuilder {

	private final ModelValidator modelValidator;

	public FavoriteRelationship build(Users usersUserId, Articles articlesArticleId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new FavoriteRelationship(usersUserId, articlesArticleId));
	}
  
}
