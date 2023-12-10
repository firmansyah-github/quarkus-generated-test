// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.comments.UpdateCommentsInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("comments")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateCommentsRequest {

	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	@NotBlank(message = ValidationMessages.COMMENTS_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
	@NotBlank(message = ValidationMessages.COMMENTS_AUTHORID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_AUTHORID_MAX_LENGTH, max = 255)
	private String authorId;
	@Size(message = ValidationMessages.COMMENTS_BODY_MAX_LENGTH, max = 255)
	private String body;
	@NotBlank(message = ValidationMessages.COMMENTS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ID_MAX_LENGTH, max = 255)
	private String id;

	public UpdateCommentsInput toUpdateCommentsInput() {
		return new UpdateCommentsInput(
    		this.createdat, this.updatedat, this.articleId, this.authorId, this.body, this.id
		);
  }
}
