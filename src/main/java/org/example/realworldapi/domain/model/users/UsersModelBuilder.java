package org.example.realworldapi.domain.model.users;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            


import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.domain.model.articles.Articles;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class UsersModelBuilder {

	private final ModelValidator modelValidator;

	public Users build(String id, String bio, String email, String image, String password, String username, List<FavoriteRelationship> favoriteRelationshipUserIdList, List<FollowRelationship> followRelationshipFollowedIdList, List<FollowRelationship> followRelationshipUserIdList, List<Comments> commentsAuthorIdList, List<Articles> articlesAuthorIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Users(id, bio, email, image, password, username, favoriteRelationshipUserIdList, followRelationshipFollowedIdList, followRelationshipUserIdList, commentsAuthorIdList, articlesAuthorIdList));
	}
  
}
