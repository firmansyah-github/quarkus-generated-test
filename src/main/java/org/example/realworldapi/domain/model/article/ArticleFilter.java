// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.article;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ArticleFilter {
  private final int offset;
  private final int limit;
  private final String loggedUserId;
  private final List<String> tags;
  private final List<String> authors;
  private final List<String> favorited;
}
