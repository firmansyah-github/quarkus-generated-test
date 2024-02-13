// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.UpdateArticlesInput;


public interface UpdateArticles {
	Articles handle(UpdateArticlesInput updateArticlesInput);
}
