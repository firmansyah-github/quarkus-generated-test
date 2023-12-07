// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.domain.model.article;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.validator.ModelValidator;

import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
public class ArticleModelBuilder {

  private final ModelValidator modelValidator;

  public Article build(String slug, String title, String description, String body, User author) {
    final var createdAt = LocalDateTime.now();
    return modelValidator.validate(
        new Article(
            UUID.randomUUID().toString(), slug, title, description, body, createdAt, createdAt, author));
  }

  public Article build(
      String id,
      String slug,
      String title,
      String description,
      String body,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      User author) {
    return modelValidator.validate(
        new Article(id, slug, title, description, body, createdAt, updatedAt, author));
  }
}

