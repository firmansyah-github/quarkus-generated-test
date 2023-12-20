// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
