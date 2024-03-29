// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.article;

import java.util.List;

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
  private final List<String> tagList;
}
