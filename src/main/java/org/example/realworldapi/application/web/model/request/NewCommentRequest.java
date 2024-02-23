// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.comment.NewCommentInput;
import org.example.realworldapi.domain.model.constants.ValidationMessages;

import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
@JsonRootName("comment")
@RegisterForReflection
public class NewCommentRequest {
  @NotBlank(message = ValidationMessages.BODY_MUST_BE_NOT_BLANK)
  private String body;

  public NewCommentInput toNewCommentInput(String authorId, String articleSlug) {
    return new NewCommentInput(authorId, articleSlug, this.body);
  }
}
