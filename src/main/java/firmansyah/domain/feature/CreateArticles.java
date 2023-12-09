// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.NewArticlesInput;

public interface CreateArticles {
	Articles handle(NewArticlesInput newArticlesInput);
}
