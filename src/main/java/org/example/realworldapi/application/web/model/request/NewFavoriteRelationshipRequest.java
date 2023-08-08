package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.favoriteRelationship.NewFavoriteRelationshipInput;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("favoriteRelationship")
@RegisterForReflection
public class NewFavoriteRelationshipRequest {
  
	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;
	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
  

	public NewFavoriteRelationshipInput toNewFavoriteRelationshipInput() {
		return new NewFavoriteRelationshipInput(
			this.userId, this.articleId
		);
  	}

}
