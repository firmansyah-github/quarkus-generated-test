package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteTags;
import org.example.realworldapi.domain.model.tags.TagsRepository;


@AllArgsConstructor
public class DeleteTagsImpl implements DeleteTags {

	private final TagsRepository tagsRepository;

  	@Override
	public boolean handle(String id) {
		return tagsRepository.delete(id);
	}
}
