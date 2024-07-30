// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.comments;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;


import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.users.Users;
import java.util.UUID;

@AllArgsConstructor
public class CommentsModelBuilder {

	private final ModelValidator modelValidator;

	public Comments build(LocalDateTime createdat, LocalDateTime updatedat, String body, String id, Articles articlesArticleId, Users usersAuthorId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Comments(createdat, updatedat, body, id, articlesArticleId, usersAuthorId));
	}
  
}
