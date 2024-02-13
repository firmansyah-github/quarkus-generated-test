// modify by the factor : Feb 13, 2024, 4:06:34 PM  
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
