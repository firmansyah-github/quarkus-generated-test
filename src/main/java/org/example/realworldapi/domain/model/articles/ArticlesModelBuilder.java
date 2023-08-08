package org.example.realworldapi.domain.model.articles;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.comments.Comments;
import java.util.List;
import java.util.stream.Collectors;

import org.example.realworldapi.domain.model.users.Users;
import java.util.UUID;

@AllArgsConstructor
public class ArticlesModelBuilder {

	private final ModelValidator modelValidator;

	public Articles build(String id, String body, String description, String slug, String title, LocalDateTime updatedat, List<FavoriteRelationship> favoriteRelationshipArticleIdList, List<TagRelationship> tagRelationshipArticleIdList, List<Comments> commentsArticleIdList, Users usersAuthorId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Articles(id, body, description, slug, title, updatedat, favoriteRelationshipArticleIdList, tagRelationshipArticleIdList, commentsArticleIdList, usersAuthorId));
	}
  
}
