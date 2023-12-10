// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.UpdateArticlesInput;


public interface UpdateArticles {
	Articles handle(UpdateArticlesInput updateArticlesInput);
}
