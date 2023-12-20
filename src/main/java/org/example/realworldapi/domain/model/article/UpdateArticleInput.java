// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
