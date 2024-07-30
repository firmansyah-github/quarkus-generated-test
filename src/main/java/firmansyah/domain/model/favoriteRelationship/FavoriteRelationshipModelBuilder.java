// created by the factor : May 30, 2024, 6:48:44 AM  
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
