// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
