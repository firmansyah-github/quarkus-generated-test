// modify by the factor : Feb 13, 2024, 4:06:34 PM  
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
