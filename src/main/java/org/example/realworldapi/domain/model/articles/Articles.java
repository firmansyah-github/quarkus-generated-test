package org.example.realworldapi.domain.model.articles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.comments.Comments;
import java.util.List;
import java.util.stream.Collectors;

import org.example.realworldapi.domain.model.users.Users;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Articles {
	@NotBlank(message = ValidationMessages.ARTICLES_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.ARTICLES_BODY_MAX_LENGTH, max = 255)
	private String body;
	@Size(message = ValidationMessages.ARTICLES_DESCRIPTION_MAX_LENGTH, max = 255)
	private String description;
	@Size(message = ValidationMessages.ARTICLES_SLUG_MAX_LENGTH, max = 255)
	private String slug;
	@Size(message = ValidationMessages.ARTICLES_TITLE_MAX_LENGTH, max = 255)
	private String title;
	private LocalDateTime updatedat;
	private List<FavoriteRelationship> favoriteRelationshipArticleIdList;
	private List<TagRelationship> tagRelationshipArticleIdList;
	private List<Comments> commentsArticleIdList;
	
	private Users usersAuthorId;
	
}
