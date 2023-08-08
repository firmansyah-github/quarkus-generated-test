package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.comments.NewCommentsInput;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;



@Getter
@Setter
@JsonRootName("comments")
@RegisterForReflection
public class NewCommentsRequest {
  
	@NotBlank(message = ValidationMessages.COMMENTS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.COMMENTS_BODY_MAX_LENGTH, max = 255)
	private String body;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	@NotBlank(message = ValidationMessages.COMMENTS_ARTICLEID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ARTICLEID_MAX_LENGTH, max = 255)
	private String articleId;
	@NotBlank(message = ValidationMessages.COMMENTS_AUTHORID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_AUTHORID_MAX_LENGTH, max = 255)
	private String authorId;
  

	public NewCommentsInput toNewCommentsInput() {
		return new NewCommentsInput(
			this.id, this.body, this.createdat, this.updatedat, this.articleId, this.authorId
		);
  	}

}
