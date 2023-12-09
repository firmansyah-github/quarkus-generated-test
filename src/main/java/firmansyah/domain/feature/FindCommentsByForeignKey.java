// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import java.util.List;



public interface FindCommentsByForeignKey {
  
	List<Comments> handleForAuthorId(java.lang.String AuthorId);
	List<Comments> handleForArticleId(java.lang.String ArticleId);
}

