// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.articles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.comments.Comments;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.users.Users;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Articles {
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	@Size(message = ValidationMessages.ARTICLES_BODY_MAX_LENGTH, max = 255)
	private String body;
	@Size(message = ValidationMessages.ARTICLES_DESCRIPTION_MAX_LENGTH, max = 255)
	private String description;
	@NotBlank(message = ValidationMessages.ARTICLES_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.ARTICLES_SLUG_MAX_LENGTH, max = 255)
	private String slug;
	@Size(message = ValidationMessages.ARTICLES_TITLE_MAX_LENGTH, max = 255)
	private String title;
	private List<FavoriteRelationship> favoriteRelationshipArticleIdList;
	private List<TagRelationship> tagRelationshipArticleIdList;
	private List<Comments> commentsArticleIdList;
	
	private Users usersAuthorId;
	
}
