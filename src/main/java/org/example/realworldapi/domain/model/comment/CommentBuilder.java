// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.comment;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.article.Article;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
public class CommentBuilder {

  private final ModelValidator modelValidator;

  public Comment build(User author, Article article, String body) {
    final var createdAt = LocalDateTime.now();
    return modelValidator.validate(
        new Comment(UUID.randomUUID().toString(), author, article, body, createdAt, createdAt));
  }

  public Comment build(
      String id,
      User author,
      Article article,
      String body,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    return modelValidator.validate(new Comment(id, author, article, body, createdAt, updatedAt));
  }
}

