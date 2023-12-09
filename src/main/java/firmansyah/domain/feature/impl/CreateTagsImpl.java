// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateTags;
import firmansyah.domain.model.tags.*;
import firmansyah.domain.exception.TagsAlreadyExistsException;
import firmansyah.domain.feature.*;



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
