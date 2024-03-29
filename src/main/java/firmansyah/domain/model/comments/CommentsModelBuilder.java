// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.comments;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;


import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.articles.Articles;
import java.util.UUID;

@AllArgsConstructor
public class CommentsModelBuilder {

	private final ModelValidator modelValidator;

	public Comments build(LocalDateTime createdat, LocalDateTime updatedat, String body, String id, Users usersAuthorId, Articles articlesArticleId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Comments(createdat, updatedat, body, id, usersAuthorId, articlesArticleId));
	}
  
}
