// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.UpdateArticlesInput;


public interface UpdateArticles {
	Articles handle(UpdateArticlesInput updateArticlesInput);
}
