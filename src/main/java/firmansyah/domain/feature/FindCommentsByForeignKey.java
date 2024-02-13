// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import java.util.List;



public interface FindCommentsByForeignKey {
  
	List<Comments> handleForAuthorId(java.lang.String AuthorId);
	List<Comments> handleForArticleId(java.lang.String ArticleId);
}

