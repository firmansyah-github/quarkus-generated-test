package org.example.realworldapi.domain.model.comments;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;


import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.articles.Articles;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comments {
	@NotBlank(message = ValidationMessages.COMMENTS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.COMMENTS_BODY_MAX_LENGTH, max = 255)
	private String body;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	
	private Users usersAuthorId;
	private Articles articlesArticleId;
	
}
