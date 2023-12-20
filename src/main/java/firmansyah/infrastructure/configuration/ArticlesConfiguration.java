// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.articles.ArticlesModelBuilder;
import firmansyah.domain.model.articles.ArticlesRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class ArticlesConfiguration {

	@Produces
  	@Singleton
  	public CreateArticles createArticles(
		ArticlesRepository articlesRepository,
      	ArticlesModelBuilder articlesBuilder ,FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey) {
    	return new CreateArticlesImpl(
        	articlesRepository,
        	articlesBuilder ,findUsersAuthorIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteArticles deleteArticles(
		ArticlesRepository articlesRepository) {
    		return new DeleteArticlesImpl(
        			articlesRepository);
  	}

  	@Produces
  	@Singleton
  	public FindArticlesByFilter findArticlesByFilter(ArticlesRepository articlesRepository) {
    	return new FindArticlesByFilterImpl(articlesRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindArticlesByPrimaryKey findArticlesByPrimaryKey(ArticlesRepository articlesRepository) {
		return new FindArticlesByPrimaryKeyImpl(articlesRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateArticles updateArticles(
		ArticlesRepository articlesRepository,
      	ArticlesModelBuilder articlesBuilder,
      	FindArticlesByPrimaryKey findArticlesByPrimaryKey	,FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey) {
		return new UpdateArticlesImpl(
        	articlesRepository,
        	articlesBuilder,
        	findArticlesByPrimaryKey ,findUsersAuthorIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public ArticlesModelBuilder articlesBuilder(ModelValidator modelValidator) {
		return new ArticlesModelBuilder(modelValidator);
  	}
}
