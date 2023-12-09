// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.favoriteRelationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.articles.Articles;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteRelationship {
	
	private Users usersUserId;
	private Articles articlesArticleId;
	
}
