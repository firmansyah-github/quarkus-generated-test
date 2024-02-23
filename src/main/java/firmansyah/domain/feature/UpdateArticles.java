// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.UpdateArticlesInput;


public interface UpdateArticles {
	Articles handle(UpdateArticlesInput updateArticlesInput);
}
