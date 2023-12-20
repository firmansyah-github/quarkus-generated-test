// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.NewArticlesInput;

public interface CreateArticles {
	Articles handle(NewArticlesInput newArticlesInput);
}
