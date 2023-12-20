// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.tags.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

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
