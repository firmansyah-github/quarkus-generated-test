// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.comments.NewCommentsInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;



@Getter
@Setter
@JsonRootName("comments")
@RegisterForReflection
public class NewCommentsRequest {
  
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
  

	public NewCommentsInput toNewCommentsInput() {
		return new NewCommentsInput(
			this.createdat, this.updatedat, this.articleId, this.authorId, this.body, this.id
		);
  	}

}
