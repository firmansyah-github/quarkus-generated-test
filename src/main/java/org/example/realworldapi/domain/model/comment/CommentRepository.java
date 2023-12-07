// modify by the factor : Dec 7, 2023, 4:02:02 PM  
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
