// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.article;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class NewArticleInput {
  private String authorId;
  private String title;
  private String description;
  private String body;
  private List<String> tagList;
}
