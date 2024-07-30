// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.users;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class UsersModelBuilder {

	private final ModelValidator modelValidator;

	public Users build(String bio, String email, String id, String image, String password, String username, List<Comments> commentsAuthorIdList, List<Articles> articlesAuthorIdList, List<FavoriteRelationship> favoriteRelationshipUserIdList, List<FollowRelationship> followRelationshipFollowedIdList, List<FollowRelationship> followRelationshipUserIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Users(bio, email, id, image, password, username, commentsAuthorIdList, articlesAuthorIdList, favoriteRelationshipUserIdList, followRelationshipFollowedIdList, followRelationshipUserIdList));
	}
  
}
