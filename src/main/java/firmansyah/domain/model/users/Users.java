// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.model.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.articles.Articles;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
	@Size(message = ValidationMessages.USERS_BIO_MAX_LENGTH, max = 255)
	private String bio;
	@Size(message = ValidationMessages.USERS_EMAIL_MAX_LENGTH, max = 255)
	private String email;
	@NotBlank(message = ValidationMessages.USERS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.USERS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.USERS_IMAGE_MAX_LENGTH, max = 255)
	private String image;
	@Size(message = ValidationMessages.USERS_PASSWORD_MAX_LENGTH, max = 255)
	private String password;
	@Size(message = ValidationMessages.USERS_USERNAME_MAX_LENGTH, max = 255)
	private String username;
	private List<FavoriteRelationship> favoriteRelationshipUserIdList;
	private List<FollowRelationship> followRelationshipFollowedIdList;
	private List<FollowRelationship> followRelationshipUserIdList;
	private List<Comments> commentsAuthorIdList;
	private List<Articles> articlesAuthorIdList;
	
	
}
