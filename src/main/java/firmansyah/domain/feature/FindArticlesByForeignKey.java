// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import java.util.List;



public interface FindArticlesByForeignKey {
  
	List<Articles> handleForAuthorId(java.lang.String AuthorId);
}

