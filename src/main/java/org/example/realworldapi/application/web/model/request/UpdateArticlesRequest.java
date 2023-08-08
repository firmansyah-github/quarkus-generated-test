package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.articles.UpdateArticlesInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("articles")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateArticlesRequest {

	@NotBlank(message = ValidationMessages.ARTICLES_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.ARTICLES_BODY_MAX_LENGTH, max = 255)
	private String body;
	@Size(message = ValidationMessages.ARTICLES_DESCRIPTION_MAX_LENGTH, max = 255)
	private String description;
	@Size(message = ValidationMessages.ARTICLES_SLUG_MAX_LENGTH, max = 255)
	private String slug;
	@Size(message = ValidationMessages.ARTICLES_TITLE_MAX_LENGTH, max = 255)
	private String title;
	private LocalDateTime updatedat;
	@NotBlank(message = ValidationMessages.ARTICLES_AUTHORID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_AUTHORID_MAX_LENGTH, max = 255)
	private String authorId;

	public UpdateArticlesInput toUpdateArticlesInput() {
		return new UpdateArticlesInput(
    		this.id, this.body, this.description, this.slug, this.title, this.updatedat, this.authorId
		);
  }
}
