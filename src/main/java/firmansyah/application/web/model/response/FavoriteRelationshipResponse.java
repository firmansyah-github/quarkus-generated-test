// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("favoriteRelationship")
@RegisterForReflection
public class FavoriteRelationshipResponse {

	private UsersResponse usersUserIdResponse;
	private ArticlesResponse articlesArticleIdResponse;
	

	public FavoriteRelationshipResponse(FavoriteRelationship favoriteRelationship,  UsersResponse usersUserIdResponse,  ArticlesResponse articlesArticleIdResponse) {
								
		this.usersUserIdResponse =usersUserIdResponse;
		this.articlesArticleIdResponse =articlesArticleIdResponse;
		

	}
    
  
}
