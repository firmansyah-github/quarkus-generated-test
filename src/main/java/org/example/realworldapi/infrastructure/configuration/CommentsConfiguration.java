package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.comments.CommentsModelBuilder;
import org.example.realworldapi.domain.model.comments.CommentsRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Dependent
public class CommentsConfiguration {

	@Produces
  	@Singleton
  	public CreateComments createComments(
		CommentsRepository commentsRepository,
      	CommentsModelBuilder commentsBuilder ,FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey) {
    	return new CreateCommentsImpl(
        	commentsRepository,
        	commentsBuilder ,findUsersAuthorIdByPrimaryKey,findArticlesArticleIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteComments deleteComments(
		CommentsRepository commentsRepository) {
    		return new DeleteCommentsImpl(
        			commentsRepository);
  	}

  	@Produces
  	@Singleton
  	public FindCommentsByFilter findCommentsByFilter(CommentsRepository commentsRepository) {
    	return new FindCommentsByFilterImpl(commentsRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindCommentsByPrimaryKey findCommentsByPrimaryKey(CommentsRepository commentsRepository) {
		return new FindCommentsByPrimaryKeyImpl(commentsRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateComments updateComments(
		CommentsRepository commentsRepository,
      	CommentsModelBuilder commentsBuilder,
      	FindCommentsByPrimaryKey findCommentsByPrimaryKey	,FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey) {
		return new UpdateCommentsImpl(
        	commentsRepository,
        	commentsBuilder,
        	findCommentsByPrimaryKey ,findUsersAuthorIdByPrimaryKey,findArticlesArticleIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public CommentsModelBuilder commentsBuilder(ModelValidator modelValidator) {
		return new CommentsModelBuilder(modelValidator);
  	}
}
