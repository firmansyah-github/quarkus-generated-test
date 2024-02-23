// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
  
  private List<String> tagList;

  public UpdateArticleInput toUpdateArticleInput(String authorId, String slug) {
    return new UpdateArticleInput(authorId, slug, this.title, this.description, this.body, this.tagList);
  }
}
