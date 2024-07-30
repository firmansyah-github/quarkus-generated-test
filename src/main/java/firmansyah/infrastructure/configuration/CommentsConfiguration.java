// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.comments.CommentsModelBuilder;
import firmansyah.domain.model.comments.CommentsRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class CommentsConfiguration {

	@Produces
  	@Singleton
  	public CreateComments createComments(
		CommentsRepository commentsRepository,
      	CommentsModelBuilder commentsBuilder ,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey,FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey) {
    	return new CreateCommentsImpl(
        	commentsRepository,
        	commentsBuilder ,findArticlesArticleIdByPrimaryKey,findUsersAuthorIdByPrimaryKey);
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
      	FindCommentsByPrimaryKey findCommentsByPrimaryKey	,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey,FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey) {
		return new UpdateCommentsImpl(
        	commentsRepository,
        	commentsBuilder,
        	findCommentsByPrimaryKey ,findArticlesArticleIdByPrimaryKey,findUsersAuthorIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public CommentsModelBuilder commentsBuilder(ModelValidator modelValidator) {
		return new CommentsModelBuilder(modelValidator);
  	}
}
