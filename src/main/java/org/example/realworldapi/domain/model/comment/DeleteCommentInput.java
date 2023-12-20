// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
