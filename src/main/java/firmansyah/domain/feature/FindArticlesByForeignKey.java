// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import java.util.List;



public interface FindArticlesByForeignKey {
  
	List<Articles> handleForAuthorId(java.lang.String AuthorId);
}

