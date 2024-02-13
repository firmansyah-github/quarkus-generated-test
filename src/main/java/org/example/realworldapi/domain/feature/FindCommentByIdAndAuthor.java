// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.comment.Comment;



public interface FindCommentByIdAndAuthor {
  Comment handle(String commentId, String authorId);
}
