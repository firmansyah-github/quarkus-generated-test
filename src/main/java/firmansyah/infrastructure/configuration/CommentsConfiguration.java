// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
