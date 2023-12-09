// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import java.util.List;



public interface FindCommentsByForeignKey {
  
	List<Comments> handleForAuthorId(java.lang.String AuthorId);
	List<Comments> handleForArticleId(java.lang.String ArticleId);
}

