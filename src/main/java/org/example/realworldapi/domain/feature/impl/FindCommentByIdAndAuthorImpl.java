// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindCommentByIdAndAuthor;
import org.example.realworldapi.domain.model.comment.Comment;
import org.example.realworldapi.domain.model.comment.CommentRepository;
import org.example.realworldapi.domain.exception.CommentNotFoundException;



@AllArgsConstructor
public class FindCommentByIdAndAuthorImpl implements FindCommentByIdAndAuthor {

  private final CommentRepository commentRepository;

  @Override
  public Comment handle(String commentId, String authorId) {
    return commentRepository
        .findByIdAndAuthor(commentId, authorId)
        .orElseThrow(CommentNotFoundException::new);
  }
}
