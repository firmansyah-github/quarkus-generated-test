// modify by the factor : Feb 22, 2024, 10:16:04 PM  
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
