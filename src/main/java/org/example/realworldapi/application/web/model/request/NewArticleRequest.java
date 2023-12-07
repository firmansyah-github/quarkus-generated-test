// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.article.NewArticleInput;

import java.util.List;


@Getter
@Setter
@JsonRootName("article")
@RegisterForReflection
public class NewArticleRequest {

  private String title;
  private String description;
  private String body;
  private List<String> tagList;

  public NewArticleInput toNewArticleInput(String loggedUserId) {
    return new NewArticleInput(loggedUserId, this.title, this.description, this.body, this.tagList);
  }
}
