// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.model.comment;

import org.example.realworldapi.domain.model.article.Article;

import java.util.List;
import java.util.Optional;


public interface CommentRepository {
  void save(Comment comment);

  Optional<Comment> findByIdAndAuthor(String commentId, String authorId);

  void delete(Comment comment);

  List<Comment> findCommentsByArticle(Article article);
}
