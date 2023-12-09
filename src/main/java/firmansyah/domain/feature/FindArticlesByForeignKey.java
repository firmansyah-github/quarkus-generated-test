// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import java.util.List;



public interface FindArticlesByForeignKey {
  
	List<Articles> handleForAuthorId(java.lang.String AuthorId);
}

