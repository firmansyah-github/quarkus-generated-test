package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.favoriteRelationship.UpdateFavoriteRelationshipInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("favoriteRelationship")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateFavoriteRelationshipRequest {

	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_USERID_MAX_LENGTH, max = 255)
	private String userId;
	@NotBlank(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.FAVORITERELATIONSHIP_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;

	public UpdateFavoriteRelationshipInput toUpdateFavoriteRelationshipInput() {
		return new UpdateFavoriteRelationshipInput(
    		this.userId, this.articleId
		);
  }
}
