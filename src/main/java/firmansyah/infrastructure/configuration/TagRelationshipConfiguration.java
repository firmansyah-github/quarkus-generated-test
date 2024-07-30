// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.tagRelationship.TagRelationshipModelBuilder;
import firmansyah.domain.model.tagRelationship.TagRelationshipRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class TagRelationshipConfiguration {

	@Produces
  	@Singleton
  	public CreateTagRelationship createTagRelationship(
		TagRelationshipRepository tagRelationshipRepository,
      	TagRelationshipModelBuilder tagRelationshipBuilder ,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey,FindTagsByPrimaryKey findTagsTagIdByPrimaryKey) {
    	return new CreateTagRelationshipImpl(
        	tagRelationshipRepository,
        	tagRelationshipBuilder ,findArticlesArticleIdByPrimaryKey,findTagsTagIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteTagRelationship deleteTagRelationship(
		TagRelationshipRepository tagRelationshipRepository) {
    		return new DeleteTagRelationshipImpl(
        			tagRelationshipRepository);
  	}

  	@Produces
  	@Singleton
  	public FindTagRelationshipByFilter findTagRelationshipByFilter(TagRelationshipRepository tagRelationshipRepository) {
    	return new FindTagRelationshipByFilterImpl(tagRelationshipRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindTagRelationshipByPrimaryKey findTagRelationshipByPrimaryKey(TagRelationshipRepository tagRelationshipRepository) {
		return new FindTagRelationshipByPrimaryKeyImpl(tagRelationshipRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateTagRelationship updateTagRelationship(
		TagRelationshipRepository tagRelationshipRepository,
      	TagRelationshipModelBuilder tagRelationshipBuilder,
      	FindTagRelationshipByPrimaryKey findTagRelationshipByPrimaryKey	,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey,FindTagsByPrimaryKey findTagsTagIdByPrimaryKey) {
		return new UpdateTagRelationshipImpl(
        	tagRelationshipRepository,
        	tagRelationshipBuilder,
        	findTagRelationshipByPrimaryKey ,findArticlesArticleIdByPrimaryKey,findTagsTagIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public TagRelationshipModelBuilder tagRelationshipBuilder(ModelValidator modelValidator) {
		return new TagRelationshipModelBuilder(modelValidator);
  	}
}
