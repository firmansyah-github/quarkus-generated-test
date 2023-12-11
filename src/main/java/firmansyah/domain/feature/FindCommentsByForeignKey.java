// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import java.util.List;



public interface FindCommentsByForeignKey {
  
	List<Comments> handleForAuthorId(java.lang.String AuthorId);
	List<Comments> handleForArticleId(java.lang.String ArticleId);
}

