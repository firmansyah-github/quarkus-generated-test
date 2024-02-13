// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class NewCommentInput {
  private String authorId;
  private String articleSlug;
  private String body;
}
