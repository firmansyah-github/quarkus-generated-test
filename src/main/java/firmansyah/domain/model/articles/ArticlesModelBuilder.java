// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.articles;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.comments.Comments;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.users.Users;
import java.util.UUID;

@AllArgsConstructor
public class ArticlesModelBuilder {

	private final ModelValidator modelValidator;

	public Articles build(LocalDateTime createdat, LocalDateTime updatedat, String body, String description, String id, String slug, String title, List<FavoriteRelationship> favoriteRelationshipArticleIdList, List<TagRelationship> tagRelationshipArticleIdList, List<Comments> commentsArticleIdList, Users usersAuthorId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Articles(createdat, updatedat, body, description, id, slug, title, favoriteRelationshipArticleIdList, tagRelationshipArticleIdList, commentsArticleIdList, usersAuthorId));
	}
  
}
