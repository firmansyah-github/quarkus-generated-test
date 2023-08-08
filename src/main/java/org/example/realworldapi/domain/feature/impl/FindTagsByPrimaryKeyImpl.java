package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindTagsByPrimaryKey;
import org.example.realworldapi.domain.exception.TagsNotFoundException;
import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.domain.model.tags.TagsRepository;


@AllArgsConstructor
public class FindTagsByPrimaryKeyImpl implements FindTagsByPrimaryKey {

	private final TagsRepository tagsRepository;

	@Override
	public Tags handle(String id) {
		return tagsRepository.findTagsByPrimaryKey(id)
    			.orElseThrow(TagsNotFoundException::new);
	}
}
