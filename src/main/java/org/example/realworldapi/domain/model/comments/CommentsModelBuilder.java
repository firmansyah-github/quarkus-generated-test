package org.example.realworldapi.domain.model.comments;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;


import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.articles.Articles;
import java.util.UUID;

@AllArgsConstructor
public class CommentsModelBuilder {

	private final ModelValidator modelValidator;

	public Comments build(String id, String body, LocalDateTime createdat, LocalDateTime updatedat, Users usersAuthorId, Articles articlesArticleId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Comments(id, body, createdat, updatedat, usersAuthorId, articlesArticleId));
	}
  
}
