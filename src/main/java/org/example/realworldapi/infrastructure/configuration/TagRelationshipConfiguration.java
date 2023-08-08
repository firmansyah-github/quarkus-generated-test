package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipModelBuilder;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

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
