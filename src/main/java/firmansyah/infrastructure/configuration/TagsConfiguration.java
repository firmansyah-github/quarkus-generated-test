// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.tags.TagsModelBuilder;
import firmansyah.domain.model.tags.TagsRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

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
