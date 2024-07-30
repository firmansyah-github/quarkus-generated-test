// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import java.util.List;



public interface FindCommentsByForeignKey {
  
	List<Comments> handleForArticleId(java.lang.String ArticleId);
	List<Comments> handleForAuthorId(java.lang.String AuthorId);
}

