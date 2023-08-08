package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




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
