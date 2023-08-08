package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.tags.TagsModelBuilder;
import org.example.realworldapi.domain.model.tags.TagsRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Dependent
public class TagsConfiguration {

	@Produces
  	@Singleton
  	public CreateTags createTags(
		TagsRepository tagsRepository,
      	TagsModelBuilder tagsBuilder ) {
    	return new CreateTagsImpl(
        	tagsRepository,
        	tagsBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteTags deleteTags(
		TagsRepository tagsRepository) {
    		return new DeleteTagsImpl(
        			tagsRepository);
  	}

  	@Produces
  	@Singleton
  	public FindTagsByFilter findTagsByFilter(TagsRepository tagsRepository) {
    	return new FindTagsByFilterImpl(tagsRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindTagsByPrimaryKey findTagsByPrimaryKey(TagsRepository tagsRepository) {
		return new FindTagsByPrimaryKeyImpl(tagsRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateTags updateTags(
		TagsRepository tagsRepository,
      	TagsModelBuilder tagsBuilder,
      	FindTagsByPrimaryKey findTagsByPrimaryKey	) {
		return new UpdateTagsImpl(
        	tagsRepository,
        	tagsBuilder,
        	findTagsByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public TagsModelBuilder tagsBuilder(ModelValidator modelValidator) {
		return new TagsModelBuilder(modelValidator);
  	}
}
