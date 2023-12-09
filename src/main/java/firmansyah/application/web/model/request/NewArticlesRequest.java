// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.articles.NewArticlesInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;



@Getter
@Setter
@JsonRootName("articles")
@RegisterForReflection
public class NewArticlesRequest {
  
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	@NotBlank(message = ValidationMessages.ARTICLES_AUTHORID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_AUTHORID_MAX_LENGTH, max = 255)
	private String authorId;
	@Size(message = ValidationMessages.ARTICLES_BODY_MAX_LENGTH, max = 255)
	private String body;
	@Size(message = ValidationMessages.ARTICLES_DESCRIPTION_MAX_LENGTH, max = 255)
	private String description;
	@NotBlank(message = ValidationMessages.ARTICLES_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.ARTICLES_SLUG_MAX_LENGTH, max = 255)
	private String slug;
	@Size(message = ValidationMessages.ARTICLES_TITLE_MAX_LENGTH, max = 255)
	private String title;
  

	public NewArticlesInput toNewArticlesInput() {
		return new NewArticlesInput(
			this.createdat, this.updatedat, this.authorId, this.body, this.description, this.id, this.slug, this.title
		);
  	}

}
