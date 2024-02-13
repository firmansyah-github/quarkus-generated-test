// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.comment.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "COMMENTS")
public class CommentEntity {

  @Id private String id;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;

  private String body;

  @ManyToOne
  @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
  private ArticleEntity article;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
  private UserEntity author;

  public CommentEntity(UserEntity author, ArticleEntity article, Comment comment) {
    this.id = comment.getId();
    this.body = comment.getBody();
    this.createdAt = comment.getCreatedAt();
    this.updatedAt = comment.getUpdatedAt();
    this.article = article;
    this.author = author;
  }
}
