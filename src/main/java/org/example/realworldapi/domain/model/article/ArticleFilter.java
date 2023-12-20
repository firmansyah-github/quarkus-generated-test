// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
