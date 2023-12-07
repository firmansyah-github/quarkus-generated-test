// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.favoriteRelationship.UpdateFavoriteRelationshipInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("favoriteRelationship")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateFavoriteRelationshipRequest {

	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;

	public UpdateFavoriteRelationshipInput toUpdateFavoriteRelationshipInput() {
		return new UpdateFavoriteRelationshipInput(
    		this.articleId, this.userId
		);
  }
}
