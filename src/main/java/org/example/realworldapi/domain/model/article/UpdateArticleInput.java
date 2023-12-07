// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.domain.model.article;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UpdateArticleInput {
  private final String authorId;
  private final String slug;
  private final String title;
  private final String description;
  private final String body;
}
