// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.article.UpdateArticleInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("article")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateArticleRequest {

  private String title;

  private String description;

  private String body;

  public UpdateArticleInput toUpdateArticleInput(String authorId, String slug) {
    return new UpdateArticleInput(authorId, slug, this.title, this.description, this.body);
  }
}
