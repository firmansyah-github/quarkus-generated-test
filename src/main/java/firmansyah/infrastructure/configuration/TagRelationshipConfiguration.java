// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
      	TagRelationshipModelBuilder tagRelationshipBuilder ,FindTagsByPrimaryKey findTagsTagIdByPrimaryKey,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey) {
    	return new CreateTagRelationshipImpl(
        	tagRelationshipRepository,
        	tagRelationshipBuilder ,findTagsTagIdByPrimaryKey,findArticlesArticleIdByPrimaryKey);
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
      	FindTagRelationshipByPrimaryKey findTagRelationshipByPrimaryKey	,FindTagsByPrimaryKey findTagsTagIdByPrimaryKey,FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey) {
		return new UpdateTagRelationshipImpl(
        	tagRelationshipRepository,
        	tagRelationshipBuilder,
        	findTagRelationshipByPrimaryKey ,findTagsTagIdByPrimaryKey,findArticlesArticleIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public TagRelationshipModelBuilder tagRelationshipBuilder(ModelValidator modelValidator) {
		return new TagRelationshipModelBuilder(modelValidator);
  	}
}
