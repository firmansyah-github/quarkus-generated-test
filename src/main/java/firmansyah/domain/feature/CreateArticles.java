// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.NewArticlesInput;

public interface CreateArticles {
	Articles handle(NewArticlesInput newArticlesInput);
}
