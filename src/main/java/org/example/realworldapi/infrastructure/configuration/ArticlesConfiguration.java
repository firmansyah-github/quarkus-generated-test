package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.articles.ArticlesModelBuilder;
import org.example.realworldapi.domain.model.articles.ArticlesRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

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
