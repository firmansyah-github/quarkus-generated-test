// modify by the factor : Dec 7, 2023, 4:02:02 PM  
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
