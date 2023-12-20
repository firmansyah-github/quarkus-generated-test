// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
