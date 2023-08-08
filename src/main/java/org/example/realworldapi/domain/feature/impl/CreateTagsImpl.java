package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateTags;
import org.example.realworldapi.domain.model.tags.*;
import org.example.realworldapi.domain.exception.TagsAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateTagsImpl implements CreateTags {

	private final TagsRepository tagsRepository;
	private final TagsModelBuilder tagsBuilder;
	

	@Override
	public Tags handle(NewTagsInput newTagsInput) {
		final var tags =
			tagsBuilder.build(newTagsInput.getId(),
					newTagsInput.getName(),
					null);
		
		if(tagsRepository.findTagsByPrimaryKey(tags.getId()).isPresent()) {
			throw new TagsAlreadyExistsException();
		} else {
			tagsRepository.save(tags);
		}
   
		return tags;
	}
}
