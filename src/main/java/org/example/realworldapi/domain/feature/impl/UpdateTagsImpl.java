package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.tags.*;
import org.example.realworldapi.domain.feature.*;

@AllArgsConstructor
public class UpdateTagsImpl implements UpdateTags {

	private final TagsRepository tagsRepository;
	private final TagsModelBuilder tagsBuilder;
	private final FindTagsByPrimaryKey findTagsByPrimaryKey;
	

	@Override
	public Tags handle(UpdateTagsInput updateTagsInput) {
		var tags = findTagsByPrimaryKey.handle(updateTagsInput.getId());
		
    	tags =
			tagsBuilder.build(updateTagsInput.getId(),
					updateTagsInput.getName(),
					null);
		tagsRepository.update(tags);
    
		return tags;
	}
}
