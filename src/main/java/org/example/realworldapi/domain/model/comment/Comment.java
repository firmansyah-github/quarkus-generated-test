// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.realworldapi.domain.model.article.Article;
import org.example.realworldapi.domain.model.user.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
  @NotNull private final String id;
  @NotNull private final User author;
  @NotNull private final Article article;
  @NotBlank private final String body;
  @NotNull private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
}
