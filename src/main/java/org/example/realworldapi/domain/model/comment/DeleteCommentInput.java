// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class DeleteCommentInput {
  private final String commentId;
  private final String authorId;
  private final String articleSlug;
}
