// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.favoriteRelationship.NewFavoriteRelationshipInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("favoriteRelationship")
@RegisterForReflection
public class NewFavoriteRelationshipRequest {
  
	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;
  

	public NewFavoriteRelationshipInput toNewFavoriteRelationshipInput() {
		return new NewFavoriteRelationshipInput(
			this.articleId, this.userId
		);
  	}

}
