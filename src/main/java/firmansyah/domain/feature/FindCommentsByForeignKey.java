// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import java.util.List;



public interface FindCommentsByForeignKey {
  
	List<Comments> handleForAuthorId(java.lang.String AuthorId);
	List<Comments> handleForArticleId(java.lang.String ArticleId);
}

